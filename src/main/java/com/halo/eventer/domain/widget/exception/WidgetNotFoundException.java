package com.halo.eventer.domain.widget.exception;

import com.halo.eventer.global.common.WidgetType;
import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class WidgetNotFoundException extends EntityNotFoundException {
    public WidgetNotFoundException(Long id, WidgetType widgetType) {
        super(String.format("%s Widget with %d is not found", widgetType.getName(), id));
    }
}
