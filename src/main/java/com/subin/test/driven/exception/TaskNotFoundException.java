package com.subin.test.driven.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException (Long id){
        super("Task not found with Id " + id);
    }

}
