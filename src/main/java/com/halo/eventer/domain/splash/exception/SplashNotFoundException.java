package com.halo.eventer.domain.splash.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class SplashNotFoundException extends EntityNotFoundException {
    public SplashNotFoundException() {
        super(String.format("Splash is not found"));
    }
}
