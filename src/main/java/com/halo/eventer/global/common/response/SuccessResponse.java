package com.halo.eventer.global.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessResponse {

    private String message;

    public SuccessResponse(SuccessCode successCode) {
        this.message = successCode.getMessage();
    }

    public static SuccessResponse of(SuccessCode successCode) {
        return new SuccessResponse(successCode);
    }
}
