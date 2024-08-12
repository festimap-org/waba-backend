package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.MissionInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampGetDto;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.MissionInfoGetApi;
import com.halo.eventer.domain.stamp.swagger.StampInfoGetApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    /** 스탬프 처음 생성 및 조회 */
    @StampInfoGetApi
    @PostMapping
    public StampInfoGetDto getStampInfo(@RequestBody StampGetDto stampGetDto) {
        return stampService.getStampInfo(stampGetDto);
    }

    @MissionInfoGetApi
    @GetMapping("/{uuid}")
    public MissionInfoGetDto getMissionInfo(@PathVariable String uuid) {
        return stampService.getMissionInfo(uuid);
    }

    @PatchMapping("/{uuid}/{missionId}")
    public String updateStamp(@PathVariable String uuid, @PathVariable int missionId) {
        return stampService.updateStamp(uuid, missionId);
    }

    @PatchMapping("/check/{uuid}")
    public String updateCheck(@PathVariable String uuid) {
        return stampService.checkFinish(uuid);
    }
}
