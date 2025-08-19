package com.halo.eventer.domain.stamp.dto.stamp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonLayout {
    ONE(0),
    TWO_ASYM(1),
    TWO_SYM(2),
    NONE(3),
    ;

    private final int code;
}
