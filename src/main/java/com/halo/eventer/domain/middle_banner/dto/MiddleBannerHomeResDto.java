package com.halo.eventer.domain.middle_banner.dto;

import com.halo.eventer.domain.middle_banner.MiddleBanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerHomeResDto {

  private String url;
  private String image;
  private Integer bannerRank;

  public MiddleBannerHomeResDto(MiddleBanner middleBanner) {
    this.url = middleBanner.getUrl();
    this.image = middleBanner.getImage();
    this.bannerRank = middleBanner.getBannerRank();
  }
}
