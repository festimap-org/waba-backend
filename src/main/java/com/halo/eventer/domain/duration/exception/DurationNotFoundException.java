package com.halo.eventer.domain.duration.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class DurationNotFoundException extends EntityNotFoundException {
    public DurationNotFoundException(Long id) {
        super(String.format("Duration with %d is not found", id));
    }
}
