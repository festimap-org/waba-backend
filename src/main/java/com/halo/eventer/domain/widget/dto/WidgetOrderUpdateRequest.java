package com.halo.eventer.domain.widget.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WidgetOrderUpdateRequest {

    private Long id;
    private int displayOrder;
}