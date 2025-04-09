package com.steve.hotel_booking_app.exceptions;


import com.steve.hotel_booking_app.common.Response;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGlobalExceptionHandler(Exception exception) {
        Response response = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Response> handleEntityExistsExceptionHandler(EntityExistsException exception) {
        Response response = Response.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleEntityExistsExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> responses = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            responses.put(fieldName, message);
        }
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(responses.toString())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundExceptionHandler(EntityNotFoundException exception) {
        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<Response> handleRessourceNotFoundExceptionHandler(RessourceNotFoundException exception) {
        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Response> handleInvalidCredentialExceptionHandler(InvalidCredentialException exception) {
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookingStateAndDateException.class)
    public ResponseEntity<Response> handleInvalidCredentialExceptionHandler(InvalidBookingStateAndDateException exception) {
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentAlreadyCompleted.class)
    public ResponseEntity<Response> handlePaymentAlreadyCompletedHandler(PaymentAlreadyCompleted exception) {
        Response response = Response.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


}
