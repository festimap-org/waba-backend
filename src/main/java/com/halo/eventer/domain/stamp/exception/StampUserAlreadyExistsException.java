package com.halo.eventer.domain.stamp.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.ConflictException;

public class StampUserAlreadyExistsException extends ConflictException {
    public StampUserAlreadyExistsException() {
        super(ErrorCode.STAMP_USER_ALREADY_EXISTS);
    }
}
