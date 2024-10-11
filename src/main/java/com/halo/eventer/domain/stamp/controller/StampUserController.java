package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.stampUser.LoginDto;
import com.halo.eventer.domain.stamp.dto.stampUser.SignupDto;
import com.halo.eventer.domain.stamp.dto.stampUser.StampUserGetDto;
import com.halo.eventer.domain.stamp.dto.stampUser.UserMissionInfoGetListDto;
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

    /** 스탬프 유저 생성 */
    @SignupApi
    @PostMapping
    public StampUserGetDto signup(@RequestParam("stampId") Long stampId, @RequestBody SignupDto signupDto) {
        return stampUserService.signup(stampId, signupDto);
    }

    /** 로그인 */
    @LoginApi
    @PostMapping("/login")
    public StampUserGetDto login(@RequestParam("stampId") Long stampId, @RequestBody LoginDto loginDto) {
        return stampUserService.login(stampId, loginDto);
    }

    /** 유저 미션 전체 조회 */
    @MissionInfoGetApi
    @GetMapping("/{uuid}")
    public UserMissionInfoGetListDto getMissionInfo(@PathVariable String uuid) {
        return stampUserService.getUserMission(uuid);
    }

    /** 사용자 미션 상태 업데이트 */
    @PatchMapping("/{uuid}/{userMissionId}")
    public String updateUserMission(@PathVariable String uuid, @PathVariable Long userMissionId) {
        return stampUserService.updateUserMission(uuid, userMissionId);
    }

    /** 사용자 미션 완료 상태 확인 */
    @StampCheckApi
    @PatchMapping("/check/{uuid}")
    public String checkFinish(@PathVariable String uuid) {
        return stampUserService.checkFinish(uuid);
    }

    /** 사용자 삭제 */
    @DeleteMapping("/{uuid}")
    public String deleteStampByUuid(@PathVariable String uuid) {
        return stampUserService.deleteStampByUuid(uuid);
    }
}
