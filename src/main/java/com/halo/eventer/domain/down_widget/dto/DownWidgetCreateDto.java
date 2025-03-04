package com.halo.eventer.domain.down_widget.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DownWidgetCreateDto {
  private Long festivalId;
  private List<DownWidgetDto> downWidgetDtos;
}
