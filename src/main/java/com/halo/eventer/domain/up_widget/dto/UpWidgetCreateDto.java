package com.halo.eventer.domain.up_widget.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpWidgetCreateDto {
    private String title;
    private String url;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
