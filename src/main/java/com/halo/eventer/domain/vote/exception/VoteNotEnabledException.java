package com.halo.eventer.domain.vote.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class VoteNotEnabledException extends BaseException {
    public VoteNotEnabledException() {
        super(ErrorCode.VOTE_NOT_ENABLED);
    }
}
