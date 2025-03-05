package com.halo.eventer.domain.concert.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class ConcertNotFoundException extends EntityNotFoundException {
    public ConcertNotFoundException(Long id) {
        super(String.format("Concert with %d is not found", id));
    }
}
