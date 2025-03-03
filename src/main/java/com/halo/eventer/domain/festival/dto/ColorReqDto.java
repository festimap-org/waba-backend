package com.halo.eventer.domain.festival.dto;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColorReqDto {
  private String mainColor;
  private String backgroundColor;
  private String subColor;
  private String fontColor;

  public ColorReqDto(Festival festival) {
    this.mainColor = festival.getMainColor();
    this.subColor = festival.getSubColor();
    this.backgroundColor = festival.getBackgroundColor();
    this.fontColor = festival.getFontColor();
  }
}
