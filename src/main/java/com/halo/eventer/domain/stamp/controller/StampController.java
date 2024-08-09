package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.MissionInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampGetDto;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.MissionInfoGetApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    @MissionInfoGetApi
    @PostMapping
    public MissionInfoGetDto getMissionInfo(@RequestBody StampGetDto stampGetDto) {
        return stampService.getMissionInfo(stampGetDto);
    }

    @PatchMapping("/{missionId}")
    public String updateStamp(@RequestBody StampGetDto stampGetDto, @PathVariable int missionId) {
        return stampService.updateStamp(stampGetDto, missionId);
    }

    @PatchMapping("/check")
    public String updateCheck(@RequestBody StampGetDto stampGetDto) {
        return stampService.checkFinish(stampGetDto);
    }
}
