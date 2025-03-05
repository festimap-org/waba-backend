package com.halo.eventer.domain.stamp.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MissionNotFoundException extends EntityNotFoundException {
    public MissionNotFoundException(Long id) {
        super(String.format("Mission with %d is not found", id));
    }
}
