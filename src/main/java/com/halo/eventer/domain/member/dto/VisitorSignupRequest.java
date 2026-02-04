package com.halo.eventer.domain.member.dto;

import com.halo.eventer.domain.member.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VisitorSignupRequest {
    // 필수 - 소셜 로그인 정보
    private SocialProvider provider;
    private String providerId;
    private String email;

    // 필수 - 기본 정보
    private String phone;
    private String name;

    // 선택 - 마케팅 동의
    private Boolean marketingSms;
    private Boolean marketingEmail;
    private Boolean marketingPush;

    // 선택 - 설문 정보
    private ResidenceType residenceType;
    private String residenceRegion;
    private String residenceDistrict;
    private VisitType visitType;
    private Gender gender;
    private AgeGroup ageGroup;
    private TransportationType transportationType;

    public boolean isMarketingSms() {
        return Boolean.TRUE.equals(marketingSms);
    }

    public boolean isMarketingEmail() {
        return Boolean.TRUE.equals(marketingEmail);
    }

    public boolean isMarketingPush() {
        return Boolean.TRUE.equals(marketingPush);
    }
}
