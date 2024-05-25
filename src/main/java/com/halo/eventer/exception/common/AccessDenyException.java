package com.halo.eventer.exception.common;

public class AccessDenyException extends RuntimeException{
    public AccessDenyException(String message) {
        super(message);
    }
}
