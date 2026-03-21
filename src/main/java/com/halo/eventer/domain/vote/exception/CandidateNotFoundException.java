package com.halo.eventer.domain.vote.exception;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

public class CandidateNotFoundException extends BaseException {
    public CandidateNotFoundException(Long id) {
        super(String.format("Candidate with %d is not found", id), ErrorCode.CANDIDATE_NOT_FOUND);
    }
}
