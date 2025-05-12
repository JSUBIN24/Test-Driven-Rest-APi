package com.subin.test.driven.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String vin) {
        super("Car with VIN " + vin + "not found in inventory");
    }

    public CarNotFoundException (String vin, Throwable cause){
        super("Car with VIN " + vin + "not found in inventory");
    }
}
