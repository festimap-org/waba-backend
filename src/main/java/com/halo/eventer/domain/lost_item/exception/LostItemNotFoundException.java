package com.halo.eventer.domain.lost_item.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class LostItemNotFoundException extends EntityNotFoundException {
    public LostItemNotFoundException(Long id) {
        super(String.format("LostItem with %d is not found", id));
    }
}
