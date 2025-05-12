package com.subin.test.driven.util;


import com.subin.test.driven.model.dto.VehicleDTO;
import jakarta.validation.Valid;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Stack;

public class ResponseEntityUtil {

    public static ResponseEntity<ApiResponse> build (ApiResponse apiResponse){
        return new ResponseEntity<>(apiResponse,apiResponse.httpStatus());
    }


    public static ApiResponse success(String message , Object response){
        return new ApiResponse(message,response,HttpStatus.OK); ///200
    }

    public static ApiResponse notFound(String message) {
        return new ApiResponse(message,null,HttpStatus.NOT_FOUND); ///404
    }

    public static ApiResponse created(String message, Object response) {
        return new ApiResponse(message,response,HttpStatus.CREATED); ///201
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(message,null,HttpStatus.INTERNAL_SERVER_ERROR); ///500
    }



    public static ApiResponse populateApiResponse(String message, Object response, HttpStatus httpStatus) {
        return new ApiResponse(message, response,httpStatus);
    }

    public static ApiResponse noContent(String message) {
        return new ApiResponse(message,null, HttpStatus.NO_CONTENT); ///204
    }
}
