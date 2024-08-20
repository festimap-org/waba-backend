package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.*;
import com.halo.eventer.domain.stamp.service.StampUserService;
import com.halo.eventer.domain.stamp.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 스탬프")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp/user")
public class StampUserController {
    private final StampUserService stampUserService;

    /** 스탬프 생성 */
    @SignupApi
    @PostMapping
    public StampUserGetDto signup(@RequestParam("stampId") Long stampId, @RequestBody SignupDto signupDto) {
        return new StampUserGetDto(stampUserService.signup(stampId, signupDto));
    }

    @LoginApi
    @PostMapping("/login")
    public StampUserGetDto login(@RequestParam("stampId") Long stampId, @RequestBody LoginDto loginDto) {
        return new StampUserGetDto(stampUserService.login(stampId, loginDto));
    }

    @MissionInfoGetApi
    @GetMapping("/{uuid}")
    public MissionInfoGetDto getMissionInfo(@PathVariable String uuid) {
        return new MissionInfoGetDto(stampUserService.getMissionInfo(uuid));
    }

    @PatchMapping("/{uuid}/{missionId}")
    public String updateStamp(@PathVariable String uuid, @PathVariable int missionId) {
        return stampUserService.updateStamp(uuid, missionId);
    }

    @StampCheckApi
    @PatchMapping("/check/{uuid}")
    public String updateCheck(@PathVariable String uuid) {
        return stampUserService.checkFinish(uuid);
    }

    @StampInfoListApi
    @GetMapping
    public StampUserInfoGetListDto getStampInfoList(@RequestParam Long stampId) {
        return stampUserService.getStampInfos(stampId);
    }

    @DeleteMapping("/{uuid}")
    public String deleteStampByUuid(@PathVariable String uuid) {
        return stampUserService.deleteStampByUuid(uuid);
    }
}
