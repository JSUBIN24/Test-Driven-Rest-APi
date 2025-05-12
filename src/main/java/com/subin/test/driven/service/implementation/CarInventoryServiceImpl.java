package com.subin.test.driven.service.implementation;

import com.subin.test.driven.exception.CarNotFoundException;
import com.subin.test.driven.exception.DuplicateCarException;
import com.subin.test.driven.model.dto.CarDTO;
import com.subin.test.driven.service.CarInventoryService;
import com.subin.test.driven.service.CarSearchCriteria;

import java.util.List;
import java.util.Optional;

public class CarInventoryServiceImpl implements CarInventoryService {


    @Override
    public void addCar(CarDTO carDTO) throws DuplicateCarException {

    }

    @Override
    public Optional<CarDTO> getCar(String vin) {
        return Optional.empty();
    }

    @Override
    public List<CarDTO> searchCars(CarSearchCriteria carSearchCriteria) {
        return List.of();
    }

    @Override
    public void removeCar(String vin) throws CarNotFoundException {

    }
}
