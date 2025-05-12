package com.subin.test.driven.repository;

import com.subin.test.driven.model.dto.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarRepository  {

 void save (CarDTO carDTO);

 Optional<CarDTO> findByVin (String vin);

 List<CarDTO> findByCriteria();
}
