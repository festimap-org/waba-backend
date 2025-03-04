package com.halo.eventer.domain.down_widget.dto;

import com.halo.eventer.domain.down_widget.DownWidget;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DownWidgetDto {
  private String name;
  private String url;

  public DownWidgetDto(DownWidget downWidget) {
    this.name = downWidget.getName();
    this.url = downWidget.getUrl();
  }
}
