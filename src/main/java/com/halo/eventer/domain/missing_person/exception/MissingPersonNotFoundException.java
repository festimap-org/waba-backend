package com.halo.eventer.domain.missing_person.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MissingPersonNotFoundException extends EntityNotFoundException {
    public MissingPersonNotFoundException(Long id) {
        super(String.format("MissingPerson with %d is not found", id));
    }
}
