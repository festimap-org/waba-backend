package com.halo.eventer.global.error;


import com.halo.eventer.global.error.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 지원하지 않는 HTTP Method로 요청시 발생
     * */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * HttpMessageConverter가 binding 하지 못할 경우 발생
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 기타 에러 예외 처리시 발생 -> 400에러로 메시지 커스텀 하고 싶을 때 사용
     * */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleElementNotFoundException(BaseException e) {
        final ErrorResponse response = ErrorResponse.of(e.getMessage(),e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
