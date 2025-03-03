package com.halo.eventer.global.exception.common;

public class AccessDenyException extends RuntimeException {
  public AccessDenyException(String message) {
    super(message);
  }
}
