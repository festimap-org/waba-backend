package com.halo.eventer.widget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.widget.dto.WidgetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String icon;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festivalId")
    @JsonIgnore
    private Festival festival;

    public Widget(WidgetDto widgetDto, Festival festival) {
        this.name = widgetDto.getName();
        this.description = widgetDto.getDescription();
        this.icon = widgetDto.getIcon();
        this.url = widgetDto.getUrl();
        this.festival = festival;
    }

    public void setWidget(WidgetDto widgetDto) {
        this.name = widgetDto.getName();
        this.description = widgetDto.getDescription();
        this.icon = widgetDto.getIcon();
        this.url = widgetDto.getUrl();
    }
}
