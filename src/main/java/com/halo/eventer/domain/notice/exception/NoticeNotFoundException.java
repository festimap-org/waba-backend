package com.halo.eventer.domain.content.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class ContentNotFoundException extends EntityNotFoundException {
    public ContentNotFoundException(Long id) {
        super(String.format("Notice with %d is not found", id));
    }
}
