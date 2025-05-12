package com.subin.test.driven.service.implementation;

import com.subin.test.driven.model.dto.VehicleDTO;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Service
public class SearchService {

    private final InventoryService inventoryService;

    public SearchService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    public List<VehicleDTO> searchVehicles(Predicate<VehicleDTO> condition){
        return inventoryService.getAlLInventory().stream()
                .filter(condition)
                .toList();
    }


    //Search by Model of vehicle
    public ApiResponse getVehicleByModel(String model){
        List<VehicleDTO> vehicleDTOS = searchVehicles(search -> search.model().equalsIgnoreCase(model));

        return vehicleDTOS.isEmpty() ?
                logAndReturnNotFound() :
                ResponseEntityUtil.success(model+ " Model found in the vehicle inventory ", vehicleDTOS);
    }

    //Search by Make
    public ApiResponse getVehicleByMake(String make){
        List<VehicleDTO> vehicleDTOS = searchVehicles(search -> search.make().equalsIgnoreCase(make));

        return vehicleDTOS.isEmpty() ?
                logAndReturnNotFound() :
                ResponseEntityUtil.success(make+ " found in the vehicle inventory ", vehicleDTOS);
    }

    private static ApiResponse logAndReturnNotFound() {
        log.info("Requested details not found in the vehicle inventory");
        return ResponseEntityUtil.notFound("Requested details not found in the vehicle inventory");
    }


    public ApiResponse getVehicleByYear(int year) {
        List<VehicleDTO> vehicleDTOByYear = searchVehicles(search -> search.year() == year);
        return vehicleDTOByYear.isEmpty() ?
                logAndReturnNotFound() :
                ResponseEntityUtil.success(year+ " Model found in the vehicle inventory ", vehicleDTOByYear);

    }

    public ApiResponse searchByPriceRange(double minPrice, double maxPrice, int page, int size, String sort) {
        List<VehicleDTO> vehicleDTOListByPriceRange = inventoryService.getAlLInventory().stream()
                .filter(vehicle -> vehicle.price() >= minPrice && vehicle.price() <= maxPrice)
                .sorted(getComparator(sort))
                .skip(size)
                .limit(page)
                .toList();

        String message = MessageFormat.format( "Inventory details min price : {0} to max price : {1} successfully retrieved ", minPrice, maxPrice);

        return vehicleDTOListByPriceRange.isEmpty() ?
                logAndReturnNotFound() :
                 ResponseEntityUtil.success(message,vehicleDTOListByPriceRange);
    }

    public ApiResponse searchByYearRange(int startYear, int endYear, int page, int size, String sort) {
        List<VehicleDTO> vehicleDTOListByYearRange = inventoryService.getAlLInventory().stream()
                .filter(vehicleDTO -> vehicleDTO.year() >= startYear && vehicleDTO.year() <= endYear)
                .sorted(getComparator(sort))
                .skip(size)
                .limit(page)
                .toList();

        System.out.println("vehicleDTOListByYearRange = " + vehicleDTOListByYearRange);

        String message = MessageFormat.format( "Inventory details from start year : {0} to end year : {1} successfully retrieved ", startYear, endYear);

        return vehicleDTOListByYearRange.isEmpty() ?
                logAndReturnNotFound() :
                ResponseEntityUtil.success(message,vehicleDTOListByYearRange);
    }

    private Comparator<VehicleDTO> getComparator(String sort) {
        boolean descending = sort.startsWith("-");
        String fieldName = descending ? sort.substring(1) : sort;

        Comparator<VehicleDTO> comparator = switch (fieldName){
            case "year" -> Comparator.comparing(VehicleDTO::year);
            case "price" -> Comparator.comparing(VehicleDTO::price);
            default -> Comparator.comparing(VehicleDTO::make);
        };

        return descending ? comparator.reversed() : comparator;
    }
}
