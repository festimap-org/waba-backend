package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.*;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    /** 스탬프 생성 */
    @SignupApi
    @PostMapping
    public StampGetDto signup(@RequestParam("festivalId") Long festivalId, @RequestBody SignupDto signupDto) {
        return stampService.signup(festivalId, signupDto);
    }

    @LoginApi
    @PostMapping("/login")
    public StampGetDto login(@RequestParam("festivalId") Long festivalId, @RequestBody LoginDto loginDto) {
        return stampService.login(festivalId, loginDto);
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

    @StampCheckApi
    @PatchMapping("/check/{uuid}")
    public String updateCheck(@PathVariable String uuid) {
        return stampService.checkFinish(uuid);
    }

    @StampInfoListApi
    @GetMapping
    public StampInfoGetListDto getStampInfoList(@RequestParam Long festivalId) {
        return stampService.getStampInfos(festivalId);
    }

    @DeleteMapping
    public String deleteStampByFestival(@RequestParam Long festivalId) {
        return stampService.deleteStamp(festivalId);
    }
}
