package com.halo.eventer.global.error.exception;

import com.halo.eventer.global.error.ErrorCode;

public class ConflictException extends BaseException {
  public ConflictException(String message) {
    super(message, ErrorCode.CONFLICT);
  }
  public ConflictException(ErrorCode errorCode) {
    super(errorCode);
  }
}
