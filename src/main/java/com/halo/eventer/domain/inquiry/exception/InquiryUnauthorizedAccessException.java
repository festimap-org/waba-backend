package com.halo.eventer.domain.inquiry.exception;

import com.halo.eventer.global.error.exception.ForbiddenException;

public class InquiryUnauthorizedAccessException extends ForbiddenException {
    public InquiryUnauthorizedAccessException() {
        super("You do not have permission.");
    }
}
