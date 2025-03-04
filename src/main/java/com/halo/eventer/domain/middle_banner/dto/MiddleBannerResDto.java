package com.halo.eventer.domain.middle_banner.dto;

import com.halo.eventer.domain.middle_banner.MiddleBanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerResDto {

    private Long id;
    private String name;
    private String url;
    private String image;
    private Integer bannerRank;

    public MiddleBannerResDto(MiddleBanner middleBanner) {
        this.id = middleBanner.getId();
        this.name = middleBanner.getName();
        this.url = middleBanner.getUrl();
        this.image = middleBanner.getImage();
        this.bannerRank = middleBanner.getBannerRank();
    }
}
