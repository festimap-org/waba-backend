package com.halo.eventer.festival.dto;


import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerDto {
    private String middleBanner;
    private String middleBannerUrl;

    public MiddleBannerDto(Festival festival) {
        this.middleBanner = festival.getMiddleBanner();
        this.middleBannerUrl = festival.getMiddleUrl();
    }
}
