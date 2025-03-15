package com.halo.eventer.domain.stamp.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class StampNotFoundException extends EntityNotFoundException {
    public StampNotFoundException(Long id) {
        super(String.format("Stamp with %d is not found", id));
    }
}
