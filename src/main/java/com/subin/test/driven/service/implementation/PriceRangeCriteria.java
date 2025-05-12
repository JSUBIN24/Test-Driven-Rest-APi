package com.subin.test.driven.service.implementation;

import com.subin.test.driven.model.dto.CarDTO;
import com.subin.test.driven.service.CarSearchCriteria;

public class PriceRangeCriteria implements CarSearchCriteria {

    private final double minPrice;
    private final double maxPrice;

    public PriceRangeCriteria(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean test(CarDTO car) {
        return car.price() >= minPrice && car.price() <= maxPrice;
    }
}
