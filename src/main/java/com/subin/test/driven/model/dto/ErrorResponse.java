package com.subin.test.driven.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponse {

    private LocalDateTime localDateTime;
    private int status;
    private String message;
}
