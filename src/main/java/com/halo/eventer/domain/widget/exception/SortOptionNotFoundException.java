package com.halo.eventer.domain.widget.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class SortOptionNotFoundException extends EntityNotFoundException {
    public SortOptionNotFoundException(String name) {
        super(String.format("SortOption %s is not found", name));
    }
}
