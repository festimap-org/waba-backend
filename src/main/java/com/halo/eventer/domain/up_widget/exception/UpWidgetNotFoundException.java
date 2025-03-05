package com.halo.eventer.domain.up_widget.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class UpWidgetNotFoundException extends EntityNotFoundException {
    public UpWidgetNotFoundException(Long id) {
        super(String.format("UpWidget with %d is not found", id));
    }
}

