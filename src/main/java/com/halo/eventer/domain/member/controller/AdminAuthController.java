package com.halo.eventer.domain.member.controller;

import com.halo.eventer.domain.member.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "관리자 인증", description = "관리자(AGENCY) 로그인 및 회원가입 API")
public class AdminAuthController {

    private final MemberService memberService;

    @Operation(
            summary = "관리자 로그인 (레거시)",
            description = "기존 관리자 로그인 API입니다. 내부 호환성을 위해 유지되며, 신규 관리자 인증은 별도의 관리자 전용 로그인 API 사용을 권장합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(memberService.login(loginDto));
    }

    @Operation(
            summary = "관리자 전용 로그인",
            description = "기관 담당자(AGENCY) 계정의 loginId와 password로 로그인하여 JWT 토큰을 발급받습니다. 일반 로그인과 별도의 관리자 전용 API입니다."
    )
    @PostMapping("/admin-login")
    public ResponseEntity<TokenDto> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(memberService.loginAdmin(request));
    }

    @Operation(summary = "관리자 회원가입", description = "기관 담당자(AGENCY) 계정을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<AgencySignupResponse> signup(@Valid @RequestBody AgencySignupRequest request) {
        Member member = memberService.signupAgency(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AgencySignupResponse(member));
    }

    @Operation(summary = "아이디 중복 검사", description = "아이디 사용 가능 여부를 확인합니다.")
    @GetMapping("/check-login-id")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId(
            @RequestParam @NotBlank(message = "아이디는 필수입니다") String loginId) {
        boolean exists = memberService.existsByLoginId(loginId);
        return ResponseEntity.ok(new LoginIdCheckResponse(!exists));
    }
}
