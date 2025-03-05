package com.halo.eventer.domain.middle_banner.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class MiddleBannerNotFoundException extends EntityNotFoundException {
    public MiddleBannerNotFoundException(Long id) {
        super(String.format("MiddleBanner with %d is not found", id));
    }
}
