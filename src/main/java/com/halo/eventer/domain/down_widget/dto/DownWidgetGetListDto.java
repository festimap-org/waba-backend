package com.halo.eventer.domain.down_widget.dto;

import com.halo.eventer.domain.down_widget.DownWidget1;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DownWidgetGetListDto {
  private List<DownWidgetDto> downWidgets;

  public DownWidgetGetListDto(List<DownWidget1> downWidget1s) {
    this.downWidgets = downWidget1s.stream().map(DownWidgetDto::new).collect(Collectors.toList());
  }
}
