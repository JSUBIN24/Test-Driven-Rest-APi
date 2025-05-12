package com.subin.test.driven.service;

import com.subin.test.driven.model.dto.CarDTO;

public interface CarSearchCriteria {
    boolean test(CarDTO car);
}
