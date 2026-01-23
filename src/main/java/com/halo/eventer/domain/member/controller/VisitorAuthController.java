package com.halo.eventer.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.member.dto.OAuthUserProfile;
import com.halo.eventer.domain.member.dto.SocialLoginResult;
import com.halo.eventer.domain.member.dto.TokenDto;
import com.halo.eventer.domain.member.dto.VisitorSignupRequest;
import com.halo.eventer.domain.member.service.KakaoOAuthService;
import com.halo.eventer.domain.member.service.NaverOAuthService;
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
    private final KakaoOAuthService kakaoOAuthService;
    private final NaverOAuthService naverOAuthService;

    @GetMapping("/kakao")
    @Operation(summary = "카카오 로그인 URL", description = "카카오 로그인 페이지 URL 반환. 프론트엔드에서 이 URL로 리다이렉트")
    public ResponseEntity<AuthUrlResponse> getKakaoAuthUrl() {
        String url = kakaoOAuthService.getAuthorizationUrl();
        return ResponseEntity.ok(new AuthUrlResponse(url));
    }

    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인증 후 콜백. 기존 회원이면 토큰 발급, 신규면 NEED_SIGNUP 응답")
    public ResponseEntity<SocialLoginResult> kakaoCallback(@RequestParam("code") String code) {
        OAuthUserProfile profile = kakaoOAuthService.getUserProfile(code);
        SocialLoginResult result = visitorAuthService.processSocialLogin(
                profile.getProvider(), profile.getProviderId(), profile.getEmail(), profile.getPhone());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/naver")
    @Operation(summary = "네이버 로그인 URL", description = "네이버 로그인 페이지 URL 반환. 프론트엔드에서 이 URL로 리다이렉트")
    public ResponseEntity<AuthUrlResponse> getNaverAuthUrl(@RequestParam("state") String state) {
        String url = naverOAuthService.getAuthorizationUrl(state);
        return ResponseEntity.ok(new AuthUrlResponse(url));
    }

    @GetMapping("/naver/callback")
    @Operation(summary = "네이버 로그인 콜백", description = "네이버 인증 후 콜백. 기존 회원이면 토큰 발급, 신규면 NEED_SIGNUP 응답")
    public ResponseEntity<SocialLoginResult> naverCallback(
            @RequestParam("code") String code, @RequestParam("state") String state) {
        OAuthUserProfile profile = naverOAuthService.getUserProfile(code, state);
        SocialLoginResult result = visitorAuthService.processSocialLogin(
                profile.getProvider(), profile.getProviderId(), profile.getEmail(), profile.getPhone());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/visitor/signup")
    @Operation(summary = "VISITOR 회원가입", description = "소셜 로그인 후 회원가입 완료 (기본정보 + 마케팅동의 + 설문)")
    public ResponseEntity<TokenDto> signup(@RequestBody VisitorSignupRequest request) {
        TokenDto token = visitorAuthService.completeSignup(request);
        return ResponseEntity.ok(token);
    }

    public record AuthUrlResponse(String url) {}
}
