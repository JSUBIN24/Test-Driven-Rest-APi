package com.subin.test.driven.controller;

import com.subin.test.driven.model.dto.VehicleDTO;
import com.subin.test.driven.service.implementation.InventoryService;
import com.subin.test.driven.service.implementation.SearchService;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;


@Slf4j
@RestController
@RequestMapping("/api/v1/vehicles/")
public class VehicleController {

    private final InventoryService inventoryService;
    private final SearchService searchService;

    public VehicleController(InventoryService inventoryService, SearchService searchService) {
        this.inventoryService = inventoryService;
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addVehicles(@Valid @RequestBody VehicleDTO vehicleDTO) {
        ApiResponse apiResponse = inventoryService.addVehicle(vehicleDTO);
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllVehicles() {
        ApiResponse apiResponse = inventoryService.getAllVehicles();
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping("model/{model}")
    public ResponseEntity<ApiResponse> getAllVehiclesByModel(@PathVariable String model) {
        ApiResponse apiResponse = searchService.getVehicleByModel(model);
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping("make/{make}")
    public ResponseEntity<ApiResponse> getAllVehiclesByMake(@PathVariable String make) {
        ApiResponse apiResponse = searchService.getVehicleByMake(make);
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping("year/{year}")
    public ResponseEntity<ApiResponse> getVehicleByYear(@PathVariable int year) {
        ApiResponse apiResponse = searchService.getVehicleByYear(year);
        return ResponseEntityUtil.build(apiResponse);
    }


    @GetMapping("/search/price-range")
    public ResponseEntity<ApiResponse> searchByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sort
    ) {
        Instant start = Instant.now();
        ApiResponse apiResponse = searchService.searchByPriceRange(minPrice, maxPrice, page, size, sort);
        Instant end = Instant.now();

        log.info("Time taken to get the record by price range : {} ", Duration.between(start,end).toMillis());
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping("/search/year-range")
    public ResponseEntity<ApiResponse> searchByYearRange(
            @RequestParam int startYear,
            @RequestParam int endYear,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "year") String sort

    ) {
        Instant start = Instant.now();
        ApiResponse apiResponse = searchService.searchByYearRange(startYear, endYear, page, size, sort);
        Instant end = Instant.now();
        log.info("Time taken to get the record by Year range : {} millisecond", Duration.between(start,end).toMillis());
        return ResponseEntityUtil.build(apiResponse);

    }


}
