package com.halo.eventer.domain.stamp.controller.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.stamp.dto.mission.request.MissionClearReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.Finished;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.SortType;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionCompletionUpdateReq;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserIdReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserInfoUpdateReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserDetailResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserSummaryResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserUserIdResDto;
import com.halo.eventer.domain.stamp.service.v2.StampUserAdminService;
import com.halo.eventer.global.common.page.PagedResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users")
public class StampUserAdminController {

    private final StampUserAdminService stampUserAdminService;

    @GetMapping
    public PagedResponse<StampUserSummaryResDto> getStampUsersFiltered(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "ALL", required = false) Finished finished,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NAME", required = false) SortType sortType) {
        return stampUserAdminService.getStampUsers(festivalId, stampId, q, finished, page, size, sortType);
    }

    @PatchMapping("/{userId}/tour-finish")
    public void updateStampUser(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @PathVariable @Min(1) long userId,
            @RequestBody @Valid MissionCompletionUpdateReq req) {
        stampUserAdminService.updateStampUserPrizeAndFinished(festivalId, stampId, userId, req);
    }

    @GetMapping("/{userId}")
    public StampUserDetailResDto getStampUser(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @PathVariable @Min(1) long userId) {
        return stampUserAdminService.getUserDetail(festivalId, stampId, userId);
    }

    @PatchMapping("/{userId}/user-mission/{userMissionId}")
    public void updateStampUserMission(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @PathVariable @Min(1) long userId,
            @PathVariable @Min(1) long userMissionId,
            @RequestBody @Valid MissionClearReqDto request) {
        stampUserAdminService.updateUserMissionState(festivalId, stampId, userId, userMissionId, request);
    }

    @PatchMapping("/{userId}")
    public void updateStampUserInfo(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @PathVariable @Min(1) long userId,
            @RequestBody @Valid StampUserInfoUpdateReqDto request) {
        stampUserAdminService.updateStampUserInfo(festivalId, stampId, userId, request);
    }

    @GetMapping("/id")
    public StampUserUserIdResDto getStampUserUuid(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @RequestBody @Valid StampUserIdReqDto request) {
        return stampUserAdminService.getStampUserId(festivalId, stampId, request.getUuid());
    }
}
