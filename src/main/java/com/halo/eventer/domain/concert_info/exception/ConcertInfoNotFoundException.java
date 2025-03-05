package com.halo.eventer.domain.concert_info.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class ConcertInfoNotFoundException extends EntityNotFoundException {
    public ConcertInfoNotFoundException(Long id) {
        super(String.format("ConcertInfo with %d is not found", id));
    }
}
