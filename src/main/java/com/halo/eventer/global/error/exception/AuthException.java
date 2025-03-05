package com.halo.eventer.global.error.exception;

import com.halo.eventer.global.error.ErrorCode;

public class AuthException extends BaseException {
    public AuthException(ErrorCode e) {
        super(e);
    }
}
