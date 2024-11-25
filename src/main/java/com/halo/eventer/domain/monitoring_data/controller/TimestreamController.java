package com.halo.eventer.domain.monitoring_data.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.halo.eventer.domain.monitoring_data.dto.monitoring.*;
import com.halo.eventer.domain.monitoring_data.dto.timestream.VisitorResponseDto;
import com.halo.eventer.domain.monitoring_data.service.TimestreamService;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor")
public class TimestreamController {

    private final TimestreamService timestreamService;
    @Value("${api.key}")
    private String apiKey;

    /** 앤드포인트 */
    @PostMapping("/ai")
    public void receiveData(@RequestParam("festivalId") Long festivalId, @RequestBody VisitorResponseDto visitorResponseDto, HttpServletRequest httpServletRequest) throws JsonProcessingException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        String headerApiKey = httpServletRequest.getHeader("X-API-KEY");
        if (headerApiKey == null || !headerApiKey.equals(apiKey)) throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
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
