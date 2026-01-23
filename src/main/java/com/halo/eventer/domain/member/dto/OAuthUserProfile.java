package com.halo.eventer.domain.member.dto;

import com.halo.eventer.domain.member.SocialProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserProfile {
    private final SocialProvider provider;
    private final String providerId;
    private final String email;
    private final String phone;
}
