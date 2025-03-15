package com.halo.eventer.domain.widget.dto.main_widget;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainWidgetCreateDto {
    private String name;
    private String url;
    private String description;
    private String image;
}
