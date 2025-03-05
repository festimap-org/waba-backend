package com.halo.eventer.domain.widget.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class WidgetNotFoundException extends EntityNotFoundException {
    public WidgetNotFoundException(Long id) {
        super(String.format("Widget with %d is not found", id));
    }
}
