package com.halo.eventer.domain.stamp.controller.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.stamp.dto.stampUser.enums.MissionCleared;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.SortType;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionCompletionUpdateReq;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserDetailResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserSummaryResDto;
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
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "ALL") MissionCleared missionCleared,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NAME") SortType sortType) {
        return stampUserAdminService.getStampUsers(festivalId, stampId, q, missionCleared, page, size, sortType);
    }

    @GetMapping("/{uuid}")
    public StampUserDetailResDto getStampUser(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampId,
            @PathVariable String uuid) {
        return stampUserAdminService.getUserDetail(festivalId, stampId, uuid);
    }

    @PatchMapping("/{userId}/missions/all")
    public StampUserDetailResDto setAllMissionsCompletion(
            @PathVariable long festivalId,
            @PathVariable long stampId,
            @PathVariable long userId,
            @RequestBody @Valid MissionCompletionUpdateReq req) {
        return stampUserAdminService.setAllMissionsCompletion(festivalId, stampId, userId, req.isComplete());
    }
}
