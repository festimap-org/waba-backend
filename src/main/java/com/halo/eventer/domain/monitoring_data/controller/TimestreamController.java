package com.halo.eventer.domain.monitoring_data.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.halo.eventer.domain.monitoring_data.dto.monitoring.*;
import com.halo.eventer.domain.monitoring_data.dto.timestream.VisitorResponseDto;
import com.halo.eventer.domain.monitoring_data.service.TimestreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor")
public class TimestreamController {

    private final TimestreamService timestreamService;

    /** 앤드포인트 */
    @PostMapping("/ai")
    public void receiveData(@RequestParam("festivalId") Long festivalId, @RequestBody VisitorResponseDto visitorResponseDto) throws JsonProcessingException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        timestreamService.receiveData(festivalId, visitorResponseDto);
    }

    /** 특정 시간 누적인원 조회 + 연령대별, 성별 */
    @GetMapping("/daily/detail")
    public DailyVisitorDetailGetDto getDailyVisitorDetail(@RequestParam("festivalId") Long festivalId) throws JsonProcessingException {
        return timestreamService.getDailyVisitorDetailCount(festivalId);
    }

    /** 시간대별 인원 조회 */
    @GetMapping("/daily/hour")
    public HourVisitorGetListDto getDailyHourVisitor(@RequestParam("festivalId") Long festivalId) {
        return timestreamService.getDailyHourVisitorCount(festivalId);
    }

    /** 날짜별 총 인원수 */
    @GetMapping("/date")
    public DateVisitorListGetDto getDateVisitor(@RequestParam("festivalId") Long festivalId) {
        return timestreamService.getDateVisitorCount(festivalId);
    }

    /** 날짜별 방문 통계: 연령대별, 성별 */
    @GetMapping("/date/detail")
    public DateVisitorDetailGetDto getDateVisitorDetail(@RequestParam("festivalId") Long festivalId) throws JsonProcessingException {
        return timestreamService.getDateVisitorDetailCount(festivalId);
    }

}
