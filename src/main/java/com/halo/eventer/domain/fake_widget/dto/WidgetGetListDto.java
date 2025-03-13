package com.halo.eventer.domain.fake_widget.dto;

import com.halo.eventer.domain.fake_widget.Widget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WidgetGetListDto {
  private List<Widget> widgets;

  public WidgetGetListDto(List<Widget> widgets) {
    this.widgets = widgets;
  }
}
