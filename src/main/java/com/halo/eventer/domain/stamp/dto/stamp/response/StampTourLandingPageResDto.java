package com.halo.eventer.domain.stamp.dto.stamp.response;

import java.util.List;

import com.halo.eventer.domain.stamp.PageTemplate;
import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.LandingPageDesignTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourLandingPageResDto {
    private LandingPageDesignTemplate designTemplate;
    private String backgroundImg;
    private String iconImg;
    private String description;
    private ButtonLayout buttonLayout;
    private List<ButtonResDto> buttons;

    public static StampTourLandingPageResDto from(PageTemplate pageTemplate) {
        return new StampTourLandingPageResDto(
                pageTemplate.getLandingPageDesignTemplate(),
                pageTemplate.getBackgroundImg(),
                pageTemplate.getIconImg(),
                pageTemplate.getDescription(),
                pageTemplate.getButtonLayout(),
                ButtonResDto.fromEntities(pageTemplate.getButtons()));
    }
}
