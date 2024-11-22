package com.halo.eventer.domain.monitoring_data.controller;

import com.halo.eventer.domain.monitoring_data.dto.monitoring.*;
import com.halo.eventer.domain.monitoring_data.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor")
public class MonitoringController {
    private final MonitoringService monitoringService;

    /** 생성 + 최대 수용 인원 저장 */
    @PostMapping("/capacity")
    public String setMaxCapacity(@RequestParam("festivalId") Long festivalID, @RequestBody MaxCapacitySetDto maxCapacitySetDto) {
        return monitoringService.setMaxCapacity(festivalID, maxCapacitySetDto);
    }

    /** 알림 기준 저장 */
    @PostMapping("/level")
    public String setAlertLevel(@RequestParam("festivalId") Long festivalId, @RequestBody AlertLevelSetDto alertLevelSetDto) {
        return monitoringService.setAlertLevel(festivalId, alertLevelSetDto);
    }

    /** 알림 기준 조회 */
    @GetMapping("/level")
    public AlertLevelsGetListDto getAlertsLevels(@RequestParam("festivalId") Long festivalId) {
        return monitoringService.getAlertLevels(festivalId);
    }

    /** 알림 기준 삭제 */
    @DeleteMapping("/level/{levelId}")
    public String deleteAlertsLevel(@PathVariable Long levelId) {
        return monitoringService.deleteAlertLevel(levelId);
    }

    /** 문자 알림 전화번호 저장 및 수정 */
    @PostMapping("/phone")
    public String setAlertPhone(@RequestParam("festivalId") Long festivalId, @RequestBody AlertPhoneSetDto alertPhoneSetDto) {
        return monitoringService.setAlertPhone(festivalId, alertPhoneSetDto);
    }

    /** 문자 알림 전화번호 조회 */
    @GetMapping("/phone")
    public AlertPhonesGetDto getAlertPhones(@RequestParam("festivalId") Long festivalId) {
        return monitoringService.getAlertPhones(festivalId);
    }
}
