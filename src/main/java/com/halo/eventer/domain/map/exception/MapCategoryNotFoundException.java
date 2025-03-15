package com.halo.eventer.domain.map.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MapCategoryNotFoundException extends EntityNotFoundException {
    public MapCategoryNotFoundException(Long id) {
        super(String.format("MapCategory with %d is not found", id));
    }
}
