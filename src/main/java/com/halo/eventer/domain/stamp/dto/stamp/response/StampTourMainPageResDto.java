package com.halo.eventer.domain.stamp.dto.stamp.response;

import java.util.List;

import com.halo.eventer.domain.stamp.PageTemplate;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MainPageDesignTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampTourMainPageResDto {
    private MainPageDesignTemplate designTemplate;
    private String backgroundImg;
    private String buttonLayout;
    private List<ButtonResDto> buttons;

    public static StampTourMainPageResDto from(PageTemplate pageTemplate) {
        return new StampTourMainPageResDto(
                pageTemplate.getMainPageDesignTemplate(),
                pageTemplate.getBackgroundImg(),
                pageTemplate.getButtonLayout().name(),
                ButtonResDto.fromEntities(pageTemplate.getButtons()));
    }
}
