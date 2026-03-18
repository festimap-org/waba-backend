package com.halo.eventer.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "AdminLoginRequest", description = "관리자 전용 로그인 요청")
public class AdminLoginRequest {

    @NotBlank
    @Schema(description = "관리자 로그인 아이디", example = "test100@naver.com")
    private String loginId;

    @NotBlank
    @Schema(description = "관리자 로그인 비밀번호", example = "test!1234")
    private String password;
}
