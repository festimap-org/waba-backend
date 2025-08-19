package com.halo.eventer.domain.stamp.dto.stamp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainPageDesignTemplate {
    GRID_Nx2(0),
    GRID_Nx3(1),
    ;
    private final int code;
}
