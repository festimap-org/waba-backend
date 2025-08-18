package com.halo.eventer.domain.stamp.dto.stamp.request;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MainPageDesignTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourMainPageReqDto {
    @NotNull
    private MainPageDesignTemplate designTemplate;

    @NotEmpty
    private String backgroundImg;

    @NotNull
    private ButtonLayout buttonLayout;

    private List<@Valid ButtonReqDto> buttons;
}
