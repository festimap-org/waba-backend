package com.halo.eventer.domain.member.dto;

import com.halo.eventer.domain.member.SocialProvider;
import lombok.Getter;

@Getter
public class SocialLoginResult {

    private final boolean isMember;
    private final String token;
    private final SocialProvider provider;
    private final String providerId;
    private final String email;
    private final String phone;

    private SocialLoginResult(
            boolean isMember, String token, SocialProvider provider, String providerId, String email, String phone) {
        this.isMember = isMember;
        this.token = token;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.phone = phone;
    }

    public static SocialLoginResult loggedIn(String token) {
        return new SocialLoginResult(true, token, null, null, null, null);
    }

    public static SocialLoginResult needSignup(SocialProvider provider, String providerId, String email, String phone) {
        return new SocialLoginResult(false, null, provider, providerId, email, phone);
    }
}
