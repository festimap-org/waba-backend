package com.halo.eventer.domain.stamp.controller;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.stamp.dto.mission.MissionDetailGetDto;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.service.MissionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stamp/mission")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public MissionDetailGetDto getMission(@RequestParam("missionId") Long missionId) {
        return missionService.getMission(missionId);
    }

    @PatchMapping
    public void updateMission(
            @RequestParam("missionId") Long missionId, @RequestBody MissionUpdateDto missionUpdateDto) {
        missionService.updateMission(missionId, missionUpdateDto);
    }
}
