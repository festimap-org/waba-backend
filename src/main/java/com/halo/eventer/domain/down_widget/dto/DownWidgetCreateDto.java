package com.halo.eventer.domain.down_widget.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DownWidgetCreateDto {
    private Long festivalId;
    private List<DownWidgetDto> downWidgetDtos;
}
