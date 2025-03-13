package com.halo.eventer.domain.down_widget.dto;

import com.halo.eventer.domain.down_widget.DownWidget1;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DownWidgetDto {
  private String name;
  private String url;

  public DownWidgetDto(DownWidget1 downWidget1) {
    this.name = downWidget1.getName();
    this.url = downWidget1.getUrl();
  }
}
