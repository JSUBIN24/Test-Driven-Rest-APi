package com.subin.test.driven.exception;

public class DuplicateCarException extends RuntimeException {

    public DuplicateCarException(String vin) {
        super("Cars with vin" + vin + "already exists in inventory");
    }
}
