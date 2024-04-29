package com.halo.eventer.exception.common;

public class NoDataInDatabaseException extends RuntimeException{
    public NoDataInDatabaseException(String message) {
        super(message);
    }
}
