package com.subin.test.driven.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public record ApiResponse (
  String message,
  Object response,
  HttpStatus httpStatus
){}
