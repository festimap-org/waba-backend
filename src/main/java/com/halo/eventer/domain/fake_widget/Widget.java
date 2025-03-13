package com.halo.eventer.domain.fake_widget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.fake_widget.dto.WidgetDto;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static Widget of(WidgetDto widgetDto, Festival festival) {
        return new Widget(widgetDto, festival);
    }
}
