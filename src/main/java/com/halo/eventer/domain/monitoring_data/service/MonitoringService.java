package com.halo.eventer.domain.monitoring_data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.monitoring_data.AlertThreshold;
import com.halo.eventer.domain.monitoring_data.MonitoringData;
import com.halo.eventer.domain.monitoring_data.dto.monitoring.*;
import com.halo.eventer.domain.monitoring_data.repository.AlertThresholdRepository;
import com.halo.eventer.domain.monitoring_data.repository.MonitoringRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@EnableAsync
public class MonitoringService {
    private final MonitoringRepository monitoringRepository;
    private final AlertThresholdRepository alertThresholdRepository;

    private final FestivalService festivalService;
    private final CacheService cacheService;
    private final MonitoringSmsService monitoringSmsService;

    public MonitoringData getMonitoringData(Long festivalId) {
        return monitoringRepository.findByFestival(festivalService.getFestival(festivalId)).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
    }

    /** 생성 + 최대 수용 인원 저장 */
    @Transactional
    public String setMaxCapacity(Long festivalId, MaxCapacitySetDto capacityDto) {
        MonitoringData monitoringData = monitoringRepository.findByFestival(festivalService.getFestival(festivalId)).orElse(new MonitoringData(festivalService.getFestival(festivalId)));
        monitoringData.setMaxCapacity(capacityDto.getCapacity());

        cacheService.updateMaxCapacityCache(festivalId, capacityDto.getCapacity());
        monitoringRepository.save(monitoringData);
        return "최대 수용인원 설정 성공";
    }

    /** 알림 기준 저장 */
    @Transactional
    public String setAlertLevel(Long festivalId, AlertLevelSetDto alertLevelSetDto) {
        MonitoringData monitoringData = getMonitoringData(festivalId);
        monitoringData.setAlertThresholds(new AlertThreshold(alertLevelSetDto.getAlertLevel()*0.01, monitoringData));

        monitoringRepository.save(monitoringData);
        return "알림 기준 설정 성공";
    }

    /** 알림 기준 조회 */
    public AlertLevelsGetListDto getAlertLevels(Long festivalId) {
        MonitoringData monitoringData = getMonitoringData(festivalId);
        List<AlertLevelGetDto> alertLevelGetDtos = monitoringData.getAlertThresholds().stream()
                .map(alert -> new AlertLevelGetDto(alert.getId(), (int)(alert.getThreshold()*100)))
                .collect(Collectors.toList());

        return new AlertLevelsGetListDto(alertLevelGetDtos);
    }

    /** 알림 기준 삭제 */
    @Transactional
    public String deleteAlertLevel(Long levelId) {
        AlertThreshold alertThreshold = alertThresholdRepository.findById(levelId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        alertThresholdRepository.delete(alertThreshold);
        return "알림 기준 삭제 완료";
    }

    /** 문자 알림 전화번호 저장 */
    @Transactional
    public String setAlertPhone(Long festivalId, AlertPhoneSetDto alertPhoneSetDto) {
        MonitoringData monitoringData = getMonitoringData(festivalId);
        if (alertPhoneSetDto.getPhone1() != null) monitoringData.setAlertPhone1(alertPhoneSetDto.getPhone1());
        if (alertPhoneSetDto.getPhone2() != null) monitoringData.setAlertPhone2(alertPhoneSetDto.getPhone2());
        if (alertPhoneSetDto.getPhone3() != null) monitoringData.setAlertPhone3(alertPhoneSetDto.getPhone3());
        if (alertPhoneSetDto.getPhone4() != null) monitoringData.setAlertPhone4(alertPhoneSetDto.getPhone4());
        if (alertPhoneSetDto.getPhone5() != null) monitoringData.setAlertPhone5(alertPhoneSetDto.getPhone5());

        monitoringRepository.save(monitoringData);
        return "문자 알림 전화번호 저장 성공";
    }

    /** 문자 알림 전화번호 조회 */
    public AlertPhonesGetDto getAlertPhones(Long festivalId) {
        MonitoringData monitoringData = getMonitoringData(festivalId);

        AlertPhonesGetDto alertPhones = new AlertPhonesGetDto(
                monitoringData.getAlertPhone1(),
                monitoringData.getAlertPhone2(),
                monitoringData.getAlertPhone3(),
                monitoringData.getAlertPhone4(),
                monitoringData.getAlertPhone5());

        return alertPhones;
    }


    /** 알림 기준에 도달했는지 확인 */
    @Transactional
    public void checkAlerts(Long festivalId, int cumulativeVisitor) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        MonitoringData monitoringData = getMonitoringData(festivalId);
        int maxCapacity = monitoringData.getMaxCapacity();

        for (AlertThreshold threshold: monitoringData.getAlertThresholds()) {
            if (cumulativeVisitor >= threshold.getThreshold() * maxCapacity &&
                    (threshold.getLastSentTime() == null || ChronoUnit.HOURS.between(threshold.getLastSentTime(), LocalDateTime.now()) >= 1)) {

                monitoringSmsService.sendAlertSms(monitoringData, festivalId, (int)(threshold.getThreshold()*100));       // 비동기로 처리

                threshold.setLastSentTime(LocalDateTime.now());
                threshold.setAlertSent(true);
            }
        }
        cacheService.updateLastVisitorCache(festivalId, cumulativeVisitor);
    }

    /** 인원이 유의미하게 증가했는지 확인 */
    public boolean isSignificantChange(Long festivalId, String timestamp, int cumulativeVisitor) {
        int lastVisitorCount = cacheService.getLastVisitorCache(festivalId, timestamp);
        return (cumulativeVisitor - lastVisitorCount) >= lastVisitorCount * 0.1;      // todo: 일단 직전보다 10% 중가했을 때만 알림 체크 로직 작동하게 설정
    }
}
