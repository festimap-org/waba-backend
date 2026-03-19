package com.halo.eventer.domain.vote.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class AlreadyVotedException extends BaseException {
    public AlreadyVotedException() {
        super(ErrorCode.ALREADY_VOTED);
    }
}
