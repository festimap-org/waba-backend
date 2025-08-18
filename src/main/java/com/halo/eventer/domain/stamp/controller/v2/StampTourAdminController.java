package com.halo.eventer.domain.stamp.controller.v2;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.stamp.dto.stamp.request.*;
import com.halo.eventer.domain.stamp.dto.stamp.response.*;
import com.halo.eventer.domain.stamp.service.v2.StampTourAdminService;
import com.halo.eventer.global.common.dto.OrderUpdateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/admin/festivals/{festivalId}/stamp-tours")
public class StampTourAdminController {

    private final StampTourAdminService stampTourAdminService;

    // stamp toyr 첫 화면

    @PostMapping
    public void createStampTour(
            @PathVariable @Min(1) long festivalId, @RequestBody @Valid StampTourCreateReqDto request) {
        stampTourAdminService.createStampTourForFestival(festivalId, request.getTitle());
    }

    @GetMapping
    public List<StampTourSummaryResDto> getStampToursList(@PathVariable @Min(1) long festivalId) {
        return stampTourAdminService.getStampTourListForFestival(festivalId);
    }

    // stamp toyr 기본 설정 화면

    @GetMapping("/{stampTourId}/settings/basic")
    public StampTourSettingBasicResDto getSettingBasicForStampTour(
            @PathVariable long festivalId, @PathVariable long stampTourId) {
        return stampTourAdminService.getStampTourSettingBasicForFestival(festivalId, stampTourId);
    }

    @PutMapping("/{stampTourId}/settings/basic")
    public void upsertBasicSettingsForStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampTourBasicUpdateReqDto request) {
        stampTourAdminService.updateBasicSettings(festivalId, stampTourId, request);
    }

    // stamp toyr 안내사항 설정

    @GetMapping("/{stampTourId}/settings/notice")
    public StampTourNotificationResDto getNotificationForStampTour(
            @PathVariable @Min(1) long festivalId, @PathVariable @Min(1) long stampTourId) {
        return stampTourAdminService.getStampTourNotification(festivalId, stampTourId);
    }

    @PutMapping("/{stampTourId}/settings/notice")
    public void upsertNotificationForStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid NotificationContentReqDto request) {
        stampTourAdminService.updateStampTourNotification(
                festivalId, stampTourId, request.getCautionContent(), request.getPersonalInformationContent());
    }

    // landing page 설정

    @GetMapping("/{stampTourId}/settings/landing")
    public StampTourLandingPageResDto getLandingPageForStampTour(
            @PathVariable @Min(1) long festivalId, @PathVariable @Min(1) long stampTourId) {
        return stampTourAdminService.getLandingPageSettings(festivalId, stampTourId);
    }

    @PutMapping("/{stampTourId}/settings/landing")
    public void upsertLandingPageForStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampTourLandingPageReqDto request) {
        stampTourAdminService.updateLandingPage(festivalId, stampTourId, request);
    }

    // main page 설정

    @GetMapping("/{stampTourId}/settings/main")
    public StampTourMainPageResDto getMainPageForStampTour(
            @PathVariable @Min(1) long festivalId, @PathVariable @Min(1) long stampTourId) {
        return stampTourAdminService.getMainPageSettings(festivalId, stampTourId);
    }

    @PutMapping("/{stampTourId}/settings/main")
    public void upsertMainPageForStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampTourMainPageReqDto request) {
        stampTourAdminService.updateMainPageSettings(festivalId, stampTourId, request);
    }

    // 참여 방법 안내

    @GetMapping("/{stampTourId}/settings/guides")
    public StampTourParticipateGuideResDto getParticipateIntroductionFromStampTour(
            @PathVariable @Min(1) long festivalId, @PathVariable @Min(1) long stampTourId) {
        return stampTourAdminService.getParticipateGuide(festivalId, stampTourId);
    }

    @PatchMapping("/{stampTourId}/settings/guides")
    public List<ParticipateGuidePageSummaryResDto> updateDisplayOrder(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody List<OrderUpdateRequest> requests) {
        return stampTourAdminService.updateDisplayOrder(festivalId, stampTourId, requests);
    }

    @PutMapping("/{stampTourId}/settings/guides")
    public void upsertParticipateIntroductionFromStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampTourParticipateGuideReqDto request) {
        stampTourAdminService.upsertParticipateGuide(festivalId, stampTourId, request);
    }

    @DeleteMapping("/{stampTourId}/settings/guides/pages/{pageId}")
    public void deleteParticipateIntroductionPageFromStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @PathVariable @Min(1) long pageId) {
        stampTourAdminService.deleteParticipateGuidePage(festivalId, stampTourId, pageId);
    }

    @PostMapping("/{stampTourId}/settings/guides/pages")
    public void createParticipationGuidePage(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @RequestBody @Valid StampTourParticipateGuidePageReqDto request) {
        stampTourAdminService.createParticipateGuidePage(festivalId, stampTourId, request);
    }

    @GetMapping("/{stampTourId}/settings/guides/pages/{pageId}")
    public ParticipateGuidePageDetailsResDto getParticipationGuidePage(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @PathVariable @Min(1) long pageId) {
        return stampTourAdminService.getParticipateGuidePageDetails(festivalId, stampTourId, pageId);
    }

    @PutMapping("/{stampTourId}/settings/guides/pages/{pageId}")
    public void upsertParticipateGuidePageFromStampTour(
            @PathVariable @Min(1) long festivalId,
            @PathVariable @Min(1) long stampTourId,
            @PathVariable @Min(1) long pageId,
            @RequestBody @Valid StampTourParticipateGuidePageReqDto request) {
        stampTourAdminService.updateParticipateGuidePage(festivalId, stampTourId, pageId, request);
    }
}
