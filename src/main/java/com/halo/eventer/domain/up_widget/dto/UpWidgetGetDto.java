package com.halo.eventer.domain.up_widget.dto;


import com.halo.eventer.domain.up_widget.UpWidget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpWidgetGetDto {

    private Long id;
    private String title;
    private String url;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public UpWidgetGetDto(UpWidget upWidget) {
        this.id = upWidget.getId();
        this.title = upWidget.getTitle();
        this.url = upWidget.getUrl();
        this.startDateTime = upWidget.getStartDateTime();
        this.endDateTime = upWidget.getEndDateTime();
    }
}
