package com.halo.eventer.domain.festival.dto;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalConcertMenuDto {
  private String summary;
  private String icon;

  private FestivalConcertMenuDto(String summary, String icon) {
    this.summary = summary;
    this.icon = icon;
  }

  public static FestivalConcertMenuDto from(Festival festival){
    return new FestivalConcertMenuDto(festival.getEntrySummary(), festival.getEntryIcon());
  }
}
