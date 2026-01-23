package com.halo.eventer.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketingConsentRequest {
    private Boolean marketingSms;
    private Boolean marketingEmail;
    private Boolean marketingPush;

    public MarketingConsentRequest(Boolean marketingSms, Boolean marketingEmail, Boolean marketingPush) {
        this.marketingSms = marketingSms;
        this.marketingEmail = marketingEmail;
        this.marketingPush = marketingPush;
    }

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
