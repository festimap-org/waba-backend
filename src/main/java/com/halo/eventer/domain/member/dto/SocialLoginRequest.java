package com.halo.eventer.domain.member.dto;

import com.halo.eventer.domain.member.SocialProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialLoginRequest {
    private SocialProvider provider;
    private String providerId;
}
