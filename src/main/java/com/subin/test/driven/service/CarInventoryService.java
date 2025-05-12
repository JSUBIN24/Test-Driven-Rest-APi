package com.subin.test.driven.service;

import com.subin.test.driven.exception.CarNotFoundException;
import com.subin.test.driven.exception.DuplicateCarException;
import com.subin.test.driven.model.dto.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarInventoryService {

    void addCar(CarDTO carDTO) throws DuplicateCarException;

    Optional<CarDTO> getCar (String vin);

    List<CarDTO> searchCars(CarSearchCriteria carSearchCriteria);

    void removeCar (String vin) throws CarNotFoundException;
}
