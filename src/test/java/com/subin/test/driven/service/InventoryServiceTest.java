package com.subin.test.driven.service;

import com.subin.test.driven.model.dto.VehicleDTO;
import com.subin.test.driven.service.implementation.InventoryService;
import com.subin.test.driven.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;


    List<VehicleDTO> vehicleInventory;

    @BeforeEach
    void beforeAll() {
        vehicleInventory = Arrays.asList(
                new VehicleDTO("BMW", "X1", 2020, 35000, 10),
                new VehicleDTO("BMW", "X2", 2021, 45000, 20),
                new VehicleDTO("BMW", "X3", 2022, 55000, 30),
                new VehicleDTO("BMW", "X4", 2023, 65000, 40),
                new VehicleDTO("BMW", "X5", 2024, 75000, 50)
        );
    }

    @Test
    void testAddVehicle_ShouldReturnCreated() {
        VehicleDTO request =  new VehicleDTO("AUDI","A1",2025,25541,7);
        ApiResponse apiResponse = inventoryService.addVehicle(request);
        System.out.println("apiResponse = " + apiResponse);
        assertNotNull(apiResponse);
        assertEquals(HttpStatus.CREATED,apiResponse.httpStatus());
        assertEquals(request,apiResponse.response());
    }

    @Test
    void getAllVehicles() {
    }

    @Test
    void getAlLInventory() {
    }
}