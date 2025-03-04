package com.halo.eventer.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalConcertMenuDto {
  private String summary;
  private String icon;

  public FestivalConcertMenuDto(String summary, String icon) {
    this.summary = summary;
    this.icon = icon;
  }
}
