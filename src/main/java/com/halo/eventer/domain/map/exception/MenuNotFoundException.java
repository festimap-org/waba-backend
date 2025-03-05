package com.halo.eventer.domain.map.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MenuNotFoundException extends EntityNotFoundException {
    public MenuNotFoundException(Long id) {
        super(String.format("Menu with %d is not found", id));
    }
}
