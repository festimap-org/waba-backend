package com.halo.eventer.domain.vote.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateCreateRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String displayName;

    private String realName;

    @Size(max = 50)
    private String description;

    @Pattern(regexp = "^https?://.+", message = "imageUrl은 http 또는 https로 시작해야 합니다.")
    @Size(max = 500)
    private String imageUrl;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 하이픈 없이 10~11자리 숫자여야 합니다.")
    private String phone;
}
