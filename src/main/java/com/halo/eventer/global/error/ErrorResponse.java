package com.halo.eventer.global.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
  private String code;
  private String message;

  private ErrorResponse(final ErrorCode code) {
    this.code = code.getCode();
    this.message = code.getMessage();
  }

  private ErrorResponse(final String message, final ErrorCode code) {
    this.code = code.getCode();
    this.message = message;
  }

  public static ErrorResponse of(final ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final String message, final ErrorCode code) {
    return new ErrorResponse(message, code);
  }
}
