package com.halo.eventer.domain.map.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MapNotFoundException extends EntityNotFoundException {
    public MapNotFoundException(Long id) {
        super(String.format("Map with %d is not found", id));
    }
}
