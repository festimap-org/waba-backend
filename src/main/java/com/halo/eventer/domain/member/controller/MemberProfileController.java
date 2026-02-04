package com.halo.eventer.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.member.dto.MarketingConsentRequest;
import com.halo.eventer.domain.member.dto.MemberSurveyRequest;
import com.halo.eventer.domain.member.service.MemberProfileService;
import com.halo.eventer.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member/profile")
@RequiredArgsConstructor
@Tag(name = "회원 프로필", description = "VISITOR 회원 프로필 관리 API")
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @PatchMapping("/marketing-consent")
    @Operation(summary = "마케팅 동의 설정 변경", description = "마케팅 동의 설정(SMS/이메일/푸시)을 변경합니다")
    public ResponseEntity<Void> updateMarketingConsent(
            @RequestBody MarketingConsentRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberProfileService.updateMarketingConsent(userDetails.getMemberId(), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/survey")
    @Operation(summary = "설문 정보 입력", description = "회원 설문 정보를 입력합니다 (거주지역, 방문형태, 성별, 연령대, 교통수단 등)")
    public ResponseEntity<Void> updateSurvey(
            @RequestBody MemberSurveyRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberProfileService.updateSurvey(userDetails.getMemberId(), request);
        return ResponseEntity.ok().build();
    }
}
