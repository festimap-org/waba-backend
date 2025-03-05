package com.halo.eventer.domain.inquiry.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class InquiryNotFoundException extends EntityNotFoundException {
    public InquiryNotFoundException(Long id) {
        super(String.format("Inquiry with %d is not found", id));
    }
}
