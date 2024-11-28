package com.halo.eventer.domain.monitoring_data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.monitoring_data.MonitoringData;
import com.halo.eventer.domain.monitoring_data.dto.monitoring.*;
import com.halo.eventer.domain.monitoring_data.dto.timestream.VisitorCountDto;
import com.halo.eventer.domain.monitoring_data.dto.timestream.VisitorResponseDto;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.timestreamwrite.TimestreamWriteClient;
import software.amazon.awssdk.services.timestreamwrite.model.*;
import software.amazon.awssdk.services.timestreamwrite.model.Record;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimestreamService {

    private final TimestreamWriteClient timestreamWriteClient;
    private final MonitoringService monitoringService;
    private final QueryService queryService;
    private final FestivalService festivalService;
    private final CacheService cacheService;
    private final String DATABASE_NAME = "monitoringDB";
    private final String TABLE_NAME = "visitor";

    /** utc 시간대로 변환 */
    public String convertToUtc(String kstTimestamp) {
        LocalDateTime localDateTime = LocalDateTime.parse(kstTimestamp, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime kstZonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));

        ZonedDateTime utcZonedDateTime = kstZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public String formatTimestamp(String timestamp) {
        return Instant.parse(timestamp)
                .atZone(ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn"));
    }

    @Transactional
    public void receiveData(Long festivalId, VisitorResponseDto responseDto) throws JsonProcessingException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        festivalService.getFestival(festivalId);

        String kstTimestamp = responseDto.getTimestamp();
        String utcTimestamp = convertToUtc(kstTimestamp);
        String epochMillis = null;
        try {
            epochMillis = String.valueOf(Instant.parse(utcTimestamp).toEpochMilli());
        } catch (DateTimeParseException e) {
            log.error("invalid date format: {}", e.getMessage());
        }

        Dimension dimension = Dimension.builder()
                .name("festival_id")
                .value(festivalId.toString())
                .build();

        List<Record> records = new ArrayList<>();

        records.add(createRecord("cumulative" + "_total", responseDto.getCumulativeVisitors().getTotal().toString(), MeasureValueType.BIGINT, epochMillis, dimension));
        records.add(createRecord("realtime" + "_total", responseDto.getRealTimeVisitors().getTotal().toString(), MeasureValueType.BIGINT, epochMillis, dimension));

        records.addAll(visitorCountAsJson("cumulative", responseDto.getCumulativeVisitors(), epochMillis, dimension));
        records.addAll(visitorCountAsJson("realtime", responseDto.getRealTimeVisitors(), epochMillis, dimension));


        if (writeRecord(records)) {
            if (monitoringService.isSignificantChange(festivalId, formatTimestamp(utcTimestamp), responseDto.getCumulativeVisitors().getTotal())) {
                log.info("인원이 유의미하게 증가됐음");
                monitoringService.checkAlerts(festivalId, responseDto.getCumulativeVisitors().getTotal());
            }
            MonitoringData monitoringData = monitoringService.getMonitoringData(festivalId);
            monitoringData.setLatestTimestamp(formatTimestamp(utcTimestamp));
        }
    }

    private JsonNode convertToJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonString);
    }

    /** 나이, 성별 데이터 JSON으로 직렬화 */
    private List<Record> visitorCountAsJson(String type, VisitorCountDto visitorCountDto, String epochMillis, Dimension dimension) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String genderCountJson = objectMapper.writeValueAsString(visitorCountDto.getGenderCount());
        String ageCountJson = objectMapper.writeValueAsString(visitorCountDto.getAgeCount());

        Record genderRecord = Record.builder()
                .dimensions(dimension)
                .measureValueType(MeasureValueType.VARCHAR)
                .measureName(type + "_genderCount")
                .measureValue(genderCountJson)
                .time(epochMillis)
                .build();

        Record ageRecord = Record.builder()
                .dimensions(dimension)
                .measureValueType(MeasureValueType.VARCHAR)
                .measureName(type + "_ageCount")
                .measureValue(ageCountJson)
                .time(epochMillis)
                .build();

        List<Record> records = new ArrayList<>();
        records.add(genderRecord);
        records.add(ageRecord);

        return records;
    }


    private Record createRecord(String measureName, String value, MeasureValueType type, String epochMillis, Dimension dimension) {
        return Record.builder()
                .dimensions(dimension)
                .measureValueType(type)
                .measureName(measureName)
                .measureValue(value)
                .time(epochMillis)
                .build();
    }

    private boolean writeRecord(List<Record> records) {
        WriteRecordsRequest writeRecordsRequest = WriteRecordsRequest.builder()
                .databaseName(DATABASE_NAME)
                .tableName(TABLE_NAME)
                .records(records)
                .build();
        try {
            WriteRecordsResponse writeRecordsResponse = timestreamWriteClient.writeRecords(writeRecordsRequest);
            System.out.println("WriteRecords Status: " + writeRecordsResponse.sdkHttpResponse().statusCode());
            return true;
        } catch (RejectedRecordsException e) {
            System.out.println("RejectedRecords: " + e);
            for (RejectedRecord rejectedRecord : e.rejectedRecords()) {
                System.out.println("Rejected Index " + rejectedRecord.recordIndex() + ": "
                        + rejectedRecord.reason());
            }
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
    }

    /** 시간대별 인원 수 */
    /**
     * todo: 일단 오전 9시를 시작시간으로 고정
     */
    public HourVisitorGetListDto getDailyHourVisitorCount(Long festivalId) {
        // 현재 KST 시간 가져오기
        LocalDateTime kstDateTime = LocalDateTime.now();
        ZonedDateTime kstZonedDateTime = kstDateTime.atZone(ZoneId.of("Asia/Seoul"));

        // KST 기준 오전 10시 설정 (endTime)
        ZonedDateTime startZonedDateTime = kstZonedDateTime.withHour(10).withMinute(0).withSecond(0).withNano(0);

        List<HourVisitorGetDto> hourlyVisitorCounts = new ArrayList<>();

        // 오전 9시부터 현재 시간까지 반복
        ZonedDateTime currentHour = startZonedDateTime;
        while (currentHour.isBefore(kstZonedDateTime) || currentHour.isEqual(kstZonedDateTime)) {
            // 각 시간대의 시작과 끝 시간을 UTC로 변환
            ZonedDateTime utcStartTime = currentHour.withZoneSameInstant(ZoneId.of("UTC"));

            // 시간대별로 realtime_total 합계 조회
            String timestamp = utcStartTime.toInstant().toString();
            int visitorCount = queryService.querySumHour(festivalId, timestamp, "realtime_total");

            hourlyVisitorCounts.add(new HourVisitorGetDto(currentHour.getHour()-1, visitorCount));

            // 다음 시간대로 이동
            currentHour = currentHour.plusHours(1);
        }
        return new HourVisitorGetListDto(hourlyVisitorCounts);
    }

    /** (축제 실시간 현황 페이지): 현재 인원 + 최대 수용 인원 + 누적 연령대별 + 누적 성별 */
    public DailyVisitorDetailGetDto getDailyVisitorDetailCount(Long festivalId) throws JsonProcessingException {
        MonitoringData monitoringData = monitoringService.getMonitoringData(festivalId);

        String formatTimestamp = monitoringData.getLatestTimestamp();
        int cumulativeTotal = queryService.bigintQueryRequestAtTime(festivalId, formatTimestamp, "cumulative_total");
        String cumulativeAge = queryService.stringQueryRequestAtTime(festivalId, formatTimestamp, "cumulative_ageCount");
        String cumulativeGender = queryService.stringQueryRequestAtTime(festivalId, formatTimestamp, "cumulative_genderCount");
        DailyVisitorDetailGetDto dailyVisitorDetailGetDto = new DailyVisitorDetailGetDto(cumulativeTotal, cacheService.getMaxCapacityCache(festivalId, monitoringData), convertToJson(cumulativeAge), convertToJson(cumulativeGender));

        return dailyVisitorDetailGetDto;
    }

    /** 날짜별 총 인원수 */
    @Transactional
    public DateVisitorListGetDto getDateVisitorCount(Long festivalId) {
        MonitoringData monitoringData = monitoringService.getMonitoringData(festivalId);

        List<Duration> durationList = festivalService.getFestival(festivalId).getDurations();
        List<DateVisitorGetDto> dateVisitorGetDtos = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for (Duration duration : durationList) {
            if (duration.getDate().isBefore(today) || duration.getDate().isEqual(today)) {
                int count = queryService.bigintQueryDate(festivalId, duration.getDate().toString(), "cumulative_total");
                setMaxCount(monitoringData, count);     // 최다 방문 인원 설정
                DateVisitorGetDto dto = new DateVisitorGetDto(duration.getDate(), count);
                dateVisitorGetDtos.add(dto);
            }
        }

        return new DateVisitorListGetDto(cacheService.getMaxCapacityCache(festivalId, monitoringData), monitoringData.getMaxCount(), dateVisitorGetDtos);
    }

    @Transactional
    public void setMaxCount(MonitoringData monitoringData, int count) {
        if (monitoringData.getMaxCount() < count) { monitoringData.setMaxCount(count); }
    }
    
    /** 날짜별 방문 통계: 연령대별, 성별 */
    public DateVisitorDetailGetDto getDateVisitorDetailCount(Long festivalId) throws JsonProcessingException {
        List<Duration> durationList = festivalService.getFestival(festivalId).getDurations();

        LocalDate today = LocalDate.now();

        int[] ages = new int[4];
        int[] genders = new int[3];
        for (Duration duration : durationList) {
            if (duration.getDate().isBefore(today) || duration.getDate().isEqual(today)) {
                ages[0] += getCumulativeAge(festivalId, duration.getDate().toString(), "teenager");
                ages[1] += getCumulativeAge(festivalId, duration.getDate().toString(), "twenties_and_thirties");
                ages[2] += getCumulativeAge(festivalId, duration.getDate().toString(), "forties_and_fifties");
                ages[3] += getCumulativeAge(festivalId, duration.getDate().toString(), "sixties_and_above");

                genders[0] += getCumulativeGender(festivalId, duration.getDate().toString(), "male");
                genders[1] += getCumulativeGender(festivalId, duration.getDate().toString(), "female");
                genders[2] += getCumulativeGender(festivalId, duration.getDate().toString(), "none");
            }
        }

        DateVisitorDetailGetDto dateVisitorDetailGetDto = new DateVisitorDetailGetDto(ages[0], ages[1], ages[2], ages[3], genders[0], genders[1], genders[2]);
        return dateVisitorDetailGetDto;
    }

    private Integer getCumulativeAge(Long festivalId, String date, String type) throws JsonProcessingException {
        String cumulativeAgeJson = queryService.stringQueryDate(festivalId, date, "cumulative_ageCount");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> ageCounts = objectMapper.readValue(cumulativeAgeJson, new TypeReference<>() {});
        return ageCounts.get(type);
    }

    private Integer getCumulativeGender(Long festivalId, String date, String type) throws JsonProcessingException {
        String cumulativeAgeJson = queryService.stringQueryDate(festivalId, date, "cumulative_genderCount");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> ageCounts = objectMapper.readValue(cumulativeAgeJson, new TypeReference<>() {});
        return ageCounts.get(type);
    }
}
