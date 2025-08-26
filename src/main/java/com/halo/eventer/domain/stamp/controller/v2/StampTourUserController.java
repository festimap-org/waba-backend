package com.halo.eventer.domain.stamp.controller.v2;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.stamp.dto.mission.response.MissionBoardResDto;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionTemplateResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.StampUserGetDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionQrVerifyReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserLoginDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserSignupReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.PrizeClaimQrResDto;
import com.halo.eventer.domain.stamp.service.v2.StampTourUserService;
import com.halo.eventer.global.security.CustomStampUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/user/festivals/{festivalId}/stamp-tours")
@Slf4j
public class StampTourUserController {

    private final StampTourUserService stampTourUserService;

    @PostMapping("/{stampTourId}/signup")
    public void stampUserSignup(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampUserSignupReqDto request) {
        stampTourUserService.signup(festivalId, stampTourId, request);
    }

    @PostMapping("/{stampTourId}/login")
    public StampUserGetDto login(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampUserLoginDto request,
            HttpServletResponse response) {
        return stampTourUserService.login(festivalId, stampTourId, request, response);
    }

    @GetMapping("/{stampTourId}/missions")
    public MissionBoardResDto getUserMissionBoard(
            @AuthenticationPrincipal CustomStampUserDetails userDetails,
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId) {
        return stampTourUserService.getMissionBoard(festivalId, stampTourId, userDetails.getUsername());
    }

    @GetMapping("/{stampTourId}/missions/{missionId}")
    public MissionTemplateResDto getMissionDetails(
            @AuthenticationPrincipal CustomStampUserDetails userDetails,
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @PathVariable @Min(1) long missionId) {
        return stampTourUserService.getMissionsTemplate(festivalId, stampTourId, missionId, userDetails.getUsername());
    }

    // TODO : QR 인증방식 변경 필요 : 토큰 방식 고려 중
    @PatchMapping("/{stampTourId}/verify/qr")
    public void successMissionVerifyByQr(
            @AuthenticationPrincipal CustomStampUserDetails userDetails,
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid MissionQrVerifyReqDto request) {
        stampTourUserService.successMissionByQr(
                festivalId, stampTourId, request.getMissionId(), userDetails.getUsername());
    }

    @GetMapping("/{stampTourId}/prizes/qr")
    public PrizeClaimQrResDto getStampUserPrizeQrInfo(
            @AuthenticationPrincipal CustomStampUserDetails userDetails,
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId) {
        return stampTourUserService.getPrizeReceiveQr(festivalId, stampTourId, userDetails.getUsername());
    }
}
