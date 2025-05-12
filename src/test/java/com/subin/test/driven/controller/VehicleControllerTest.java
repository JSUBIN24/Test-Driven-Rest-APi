package com.subin.test.driven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subin.test.driven.model.dto.VehicleDTO;
import com.subin.test.driven.service.InventoryService;
import com.subin.test.driven.service.SearchService;
import com.subin.test.driven.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @MockitoBean
    InventoryService inventoryService;
    @MockitoBean
    SearchService searchService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    List<VehicleDTO> vehicleInventory;

    @BeforeEach

    void init(){
        vehicleInventory = Arrays.asList(
                new VehicleDTO("BMW", "X1", 2020, 35000, 10),
                new VehicleDTO("BMW", "X2", 2021, 45000, 20),
                new VehicleDTO("BMW", "X3", 2022, 55000, 30),
                new VehicleDTO("BMW", "X4", 2023, 65000, 40),
                new VehicleDTO("BMW", "X5", 2024, 75000, 50)
        );
    }




    @Test
    void testAddVehicles() throws Exception {
        VehicleDTO request = new VehicleDTO("BMW","X5",2025,55000,15);
        ApiResponse apiResponse = new ApiResponse("Successfully stored the vehicle",request,HttpStatus.CREATED);
        Mockito.when(inventoryService.addVehicle(request)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()
                );
    }

    @Test
    void testGetAllVehicles() throws Exception {

        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        ApiResponse apiResponse = new ApiResponse("Successfully retrieved the vehicle details",vehicleDTOS,HttpStatus.OK);
        Mockito.when(inventoryService.getAllVehicles()).thenReturn(apiResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testGetAllVehiclesByModel() throws Exception {
        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        String model = "X1";
        ApiResponse apiResponse = new ApiResponse(model+ " Model found in the vehicle inventory ",vehicleDTOS,HttpStatus.OK);
        Mockito.when(searchService.getVehicleByModel(model)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/model/{model}",model)) //Path Variable
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllVehiclesByMake() throws Exception {
        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        String make = "BMW";
        ApiResponse apiResponse = new ApiResponse(make+ " Model found in the vehicle inventory ",vehicleDTOS,HttpStatus.OK);
        Mockito.when(searchService.getVehicleByMake(make)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/make/{make}",make)) //Path Variable
                .andExpect(status().isOk());
    }

    @Test
    void testGetVehicleByYear() throws Exception {
        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        int year = 2021;
        ApiResponse apiResponse = new ApiResponse(year+ " Model found in the vehicle inventory ",vehicleDTOS,HttpStatus.OK);
        Mockito.when(searchService.getVehicleByYear(year)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/year/{year}",year)) //Path Variable
                .andExpect(status().isOk());
    }

    @Test
    void testSearchByPriceRange() throws Exception{
        //Given
        double minPrice = 25000.0;
        double maxPrice = 25000.0;
        int page = 1;
        int size = 10;
        String sort = "price";

        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        String message = MessageFormat.format( "Inventory details min price : {0} to max price : {1} successfully retrieved ", minPrice, maxPrice);
        ApiResponse apiResponse = new ApiResponse(message,vehicleDTOS,HttpStatus.OK);

        Mockito.when(searchService.searchByPriceRange(minPrice, maxPrice, page, size, sort)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/search/price-range")
                .param("minPrice", String.valueOf(minPrice))
                .param("maxPrice", String.valueOf(maxPrice))
                .param("page",String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("sort",sort)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void searchByYearRange() throws Exception{

        int startYear = 2023;
        int endYear = 2025;
        int page = 1;
        int size = 10;
        String sort = "price";

        Collection<VehicleDTO> vehicleDTOS = Collections.unmodifiableCollection(vehicleInventory);
        String message = MessageFormat.format( "Inventory details from start year : {0} to end year : {1} successfully retrieved ", startYear, endYear);
        ApiResponse apiResponse = new ApiResponse(message,vehicleDTOS,HttpStatus.OK);

        Mockito.when(searchService.searchByYearRange(startYear, endYear, page, size, sort)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/search/year-range")
                        .param("startYear", String.valueOf(startYear))
                        .param("endYear", String.valueOf(endYear))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}