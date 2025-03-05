package com.halo.eventer.domain.notice.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class NoticeNotFoundException extends EntityNotFoundException {
    public NoticeNotFoundException(Long id) {
        super(String.format("Notice with %d is not found", id));
    }
}
