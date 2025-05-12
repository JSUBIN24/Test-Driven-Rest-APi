package com.subin.test.driven.exception;

import com.subin.test.driven.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFound(Exception ex){
        return ex.getMessage();
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaskNotFound (Exception e){
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidException(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();

        for (FieldError error : exception.getFieldErrors()) {
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogic (Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();


        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicateCarException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCarException(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }



}