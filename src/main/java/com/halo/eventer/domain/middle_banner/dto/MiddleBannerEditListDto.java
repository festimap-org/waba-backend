package com.halo.eventer.domain.middle_banner.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiddleBannerEditListDto {
  private List<MiddleBannerRankEditDto> bannerRankEditDtos;
}
