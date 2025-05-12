package com.subin.test.driven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long empId){
        super("Employee Not found in DB " + empId);
    }

}
