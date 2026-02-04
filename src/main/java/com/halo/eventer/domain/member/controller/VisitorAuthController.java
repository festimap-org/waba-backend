package com.halo.eventer.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.member.dto.SocialLoginRequest;
import com.halo.eventer.domain.member.dto.SocialLoginResult;
import com.halo.eventer.domain.member.dto.TokenDto;
import com.halo.eventer.domain.member.dto.VisitorSignupRequest;
import com.halo.eventer.domain.member.service.VisitorAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Visitor 인증", description = "VISITOR 회원 인증 API")
public class VisitorAuthController {

    private final VisitorAuthService visitorAuthService;

    @PostMapping("/login")
    @Operation(
            summary = "소셜 로그인",
            description = "프론트에서 받은 provider/providerId로 로그인. 기존 회원이면 토큰 발급, 신규면 isMember=false 응답")
    public ResponseEntity<SocialLoginResult> login(@RequestBody SocialLoginRequest request) {
        SocialLoginResult result =
                visitorAuthService.processSocialLogin(request.getProvider(), request.getProviderId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    @Operation(summary = "VISITOR 회원가입", description = "소셜 로그인 후 회원가입 완료 (기본정보 + 마케팅동의 + 설문)")
    public ResponseEntity<TokenDto> signup(@RequestBody VisitorSignupRequest request) {
        TokenDto token = visitorAuthService.completeSignup(request);
        return ResponseEntity.ok(token);
    }
}
