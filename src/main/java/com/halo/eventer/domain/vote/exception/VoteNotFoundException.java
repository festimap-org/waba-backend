package com.halo.eventer.domain.vote.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class VoteNotFoundException extends BaseException {
    public VoteNotFoundException(Long id) {
        super(String.format("Vote with %d is not found", id), ErrorCode.VOTE_NOT_FOUND);
    }
}
