package com.halo.eventer.domain.vote.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class VoteCancelNotAllowedException extends BaseException {
    public VoteCancelNotAllowedException() {
        super(ErrorCode.VOTE_CANCEL_NOT_ALLOWED);
    }
}
