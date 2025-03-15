package com.halo.eventer.domain.widget_item.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class WidgetItemNotFoundException extends EntityNotFoundException {
    public WidgetItemNotFoundException(Long id) {
        super(String.format("WidgetItem with %d is not found", id));
    }
}
