package com.halo.eventer.domain.member.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class OAuthException extends BaseException {

    public OAuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OAuthException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }
}
