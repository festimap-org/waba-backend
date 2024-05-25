package com.halo.eventer.exception;

import com.halo.eventer.exception.common.AccessDenyException;
import com.halo.eventer.exception.common.DuplicatedElementException;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicatedElementException.class})
    public ResponseEntity<String> handleDuplicatedElement(DuplicatedElementException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({NoDataInDatabaseException.class})
    public ResponseEntity<String> handleNoDataInDatabase(NoDataInDatabaseException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({AccessDenyException.class})
    public ResponseEntity<String> handleAccessDenyException(AccessDenyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
