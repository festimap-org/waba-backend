package com.halo.eventer.domain.middle_banner.dto;

import com.halo.eventer.domain.middle_banner.MiddleBanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerElementDto {
  private Long id;
  private String name;
  private Integer bannerRank;

  public MiddleBannerElementDto(MiddleBanner middleBanner) {
    this.id = middleBanner.getId();
    this.name = middleBanner.getName();
    this.bannerRank = middleBanner.getBannerRank();
  }
}
