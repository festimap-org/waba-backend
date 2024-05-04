package com.halo.eventer.widget.dto;


import com.halo.eventer.widget.Widget;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WidgetDto {
    private String name;
    private String description;
    private String icon;
    private String url;

    public WidgetDto(Widget widget) {
        this.name = widget.getName();
        this.description = widget.getDescription();
        this.icon = widget.getIcon();
        this.url = widget.getUrl();
    }
}
