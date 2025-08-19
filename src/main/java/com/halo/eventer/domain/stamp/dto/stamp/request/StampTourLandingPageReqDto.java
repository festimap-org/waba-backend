package com.halo.eventer.domain.stamp.dto.stamp.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.LandingPageDesignTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourLandingPageReqDto {
    @NotNull
    private LandingPageDesignTemplate designTemplate;

    @NotEmpty
    private String backgroundImg;

    @NotEmpty
    private String iconImg;

    @NotEmpty
    private String description;

    @NotNull
    private ButtonLayout buttonLayout;

    private List<ButtonReqDto> buttons;
}
