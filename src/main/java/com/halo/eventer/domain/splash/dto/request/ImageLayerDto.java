package com.halo.eventer.domain.splash.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class ImageLayerDto {
    @NotBlank
    private String url;

    @NotBlank
    private String layerType;
}
