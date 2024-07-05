package com.halo.eventer.global.common.response;

import lombok.Getter;

@Getter
public enum SuccessCode {
    SAVE_SUCCESS("Successfully saved")
    ;

    private String message;

    SuccessCode(String message) {
        this.message = message;
    }
}