package com.backend.android.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GloblalExceptionHandler {

    // 404 not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResponseNotFoundException(ResourceNotFoundException RnFe){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(RnFe.getMessage());
    }

    //409 conflict
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException DRE){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(DRE.getMessage());
    }

    // 500 internal server
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocurrió un error inesperado en el servidor D:" + exception.getMessage());
    }

}
