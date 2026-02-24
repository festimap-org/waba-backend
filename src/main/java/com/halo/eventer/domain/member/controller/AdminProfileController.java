package com.halo.eventer.domain.member.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.dto.AdminProfileResponse;
import com.halo.eventer.domain.member.dto.AdminProfileUpdateRequest;
import com.halo.eventer.domain.member.dto.PasswordChangeRequest;
import com.halo.eventer.domain.member.service.MemberService;
import com.halo.eventer.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/profile")
@RequiredArgsConstructor
@Tag(name = "관리자 프로필", description = "관리자(AGENCY, SUPER_ADMIN) 프로필 관리 API")
public class AdminProfileController {

    private final MemberService memberService;

    @Operation(summary = "내 프로필 조회", description = "로그인한 관리자의 프로필 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<AdminProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = memberService.getAdminProfile(userDetails.getMemberId());
        return ResponseEntity.ok(new AdminProfileResponse(member));
    }

    @Operation(summary = "프로필 수정", description = "관리자 프로필 정보(회사명, 담당자 정보 등)를 수정합니다.")
    @PutMapping("/me")
    public ResponseEntity<Void> updateProfile(
            @Valid @RequestBody AdminProfileUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.updateAdminProfile(userDetails.getMemberId(), request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호 확인 후 새 비밀번호로 변경합니다.")
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody PasswordChangeRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.changePassword(userDetails.getMemberId(), request);
        return ResponseEntity.ok().build();
    }
}
