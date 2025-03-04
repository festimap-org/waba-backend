package com.halo.eventer.domain.widget.dto;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.widget.Widget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class WidgetGetDto {
    private String name;
    private String description;
    private String icon;
    private String url;
    private Festival festival;

    public WidgetGetDto(Widget widget) {
        this.name = widget.getName();
        this.description = widget.getDescription();
        this.icon = widget.getIcon();
        this.url = widget.getUrl();
        this.festival = widget.getFestival();
    }

    public static List<WidgetGetDto> fromWidgetList(List<Widget> widgets) {
        return widgets.stream()
                .map(WidgetGetDto::new)
                .collect(Collectors.toList());
    }
}
