package com.halo.eventer.domain.up_widget.dto;

import com.halo.eventer.domain.up_widget.UpWidget;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpWidgetGetListDto {
  List<UpWidgetGetDto> list;

  public UpWidgetGetListDto(List<UpWidget> list) {
    this.list = list.stream().map(UpWidgetGetDto::new).collect(Collectors.toList());
  }
}
