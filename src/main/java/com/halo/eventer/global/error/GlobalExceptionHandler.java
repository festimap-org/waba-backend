package com.halo.eventer.global.error;

import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.exception.common.AccessDenyException;
import com.halo.eventer.global.exception.common.DuplicatedElementException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import java.io.IOException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // HTTP Method 불일치 에러
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
          HttpRequestMethodNotSupportedException e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus()).body(response);
  }

  // @RequestBody JSON 파싱 에러
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_JSON_FORMAT);
    return ResponseEntity.status(ErrorCode.INVALID_JSON_FORMAT.getStatus()).body(response);
  }

  // 필수 @RequestParam 누락
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_JSON_FORMAT);
    return ResponseEntity.status(ErrorCode.INVALID_JSON_FORMAT.getStatus()).body(response);
  }

  // @RequestParam 타입 불일치
  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_PARAMETER_TYPE);
    return ResponseEntity.status(ErrorCode.INVALID_PARAMETER_TYPE.getStatus()).body(response);
  }

  // 최상위 domain error
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleElementNotFoundException(BaseException e) {
    final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  // Exception 핸들링
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(response);
  }
}
