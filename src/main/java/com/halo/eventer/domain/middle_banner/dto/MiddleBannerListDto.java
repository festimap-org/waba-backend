package com.halo.eventer.domain.middle_banner.dto;

import com.halo.eventer.domain.middle_banner.MiddleBanner;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerListDto {
  private List<MiddleBannerElementDto> middleBannerList;

  public MiddleBannerListDto(List<MiddleBanner> middleBannerList) {
    this.middleBannerList =
        middleBannerList.stream().map(MiddleBannerElementDto::new).collect(Collectors.toList());
  }
}
