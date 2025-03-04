package com.halo.eventer.domain.down_widget.dto;

import com.halo.eventer.domain.down_widget.DownWidget;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DownWidgetGetListDto {
  private List<DownWidgetDto> downWidgets;

  public DownWidgetGetListDto(List<DownWidget> downWidgets) {
    this.downWidgets = downWidgets.stream().map(DownWidgetDto::new).collect(Collectors.toList());
  }
}
