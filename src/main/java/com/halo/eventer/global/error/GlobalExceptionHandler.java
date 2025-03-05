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

  /** 지원하지 않는 HTTP Method로 요청시 발생 */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  // @RequestBody JSON 파싱 에러
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ErrorCode.INVALID_JSON_FORMAT));
  }

  // 필수 @RequestParam 누락
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ErrorCode.MISSING_PARAMETER));
  }

  // @RequestParam 타입 불일치
  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ErrorCode.INVALID_PARAMETER_TYPE));
  }

  /** 기타 에러 예외 처리시 발생 -> 400에러로 메시지 커스텀 하고 싶을 때 사용 */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleElementNotFoundException(BaseException e) {
    final ErrorResponse response = ErrorResponse.of(e.getMessage(), e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /** IOException */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOException(IOException ex) {
    // 로깅 또는 추가 처리를 할 수 있습니다.
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("입출력 예외가 발생했습니다: " + ex.getMessage());
  }
}
