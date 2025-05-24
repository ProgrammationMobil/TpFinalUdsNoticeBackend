package com.UDSNotice.BackendUdsNotice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(PublicationNotFoundException.class)
    public ResponseEntity<ApiError> handlePublicationExceptionNotFound (PublicationNotFoundException e){
        ApiError apierror = new ApiError();
        apierror.setMessage(e.getMessage());
        apierror.setCode(HttpStatus.NOT_FOUND.value());
        apierror.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apierror, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException (Exception e){
        ApiError apierror = new ApiError();
        apierror.setMessage(e.getMessage());
        apierror.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apierror.setTimestamp(LocalDateTime.now());

    return new ResponseEntity<>(apierror, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
