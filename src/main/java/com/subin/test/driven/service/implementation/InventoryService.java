package com.subin.test.driven.service.implementation;

import com.subin.test.driven.model.dto.VehicleDTO;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class InventoryService {
    private final List<VehicleDTO> vehicleInventory = new ArrayList<>(List.of(               //using Arrays.asList will make immutable so while adding it we will get exception
            new VehicleDTO("BMW", "X1", 2020, 35000, 10),
            new VehicleDTO("BMW", "X2", 2021, 45000, 20),
            new VehicleDTO("BMW", "X3", 2022, 55000, 30),
            new VehicleDTO("BMW", "X4", 2023, 65000, 40),
            new VehicleDTO("BMW", "X5", 2024, 75000, 50)
    ));

    public ApiResponse addVehicle(@Valid VehicleDTO vehicleDTO) {
        try {
            vehicleInventory.add(vehicleDTO);
            log.debug("Successfully stored the vehicle : {} ", vehicleDTO);
            return ResponseEntityUtil.created("Successfully stored the vehicle", vehicleDTO);
        } catch (Exception e) {
            log.warn("Exception occurred while storing the vehicle : {} ", e.getMessage());
            return ResponseEntityUtil.error("Exception occurred while storing the vehicle"); //500
        }
    }

    public ApiResponse getAllVehicles() {
        try {
            Collection<VehicleDTO> allVehicleDetails = Collections.unmodifiableCollection(vehicleInventory);
            log.debug("Successfully retrieved the vehicle details : {} ", allVehicleDetails);

            return allVehicleDetails.isEmpty() ?
                    ResponseEntityUtil.noContent("No records in the inventory") :
                    ResponseEntityUtil.success("Successfully retrieved the vehicle details", allVehicleDetails);

        } catch (Exception e) {
            log.warn("Exception occurred while fetching all vehicle details : {} ", e.getMessage());
            return ResponseEntityUtil.error("Exception occurred while fetching all vehicle details");
        }
    }

    public List<VehicleDTO> getAlLInventory() {
        return Collections.unmodifiableList(vehicleInventory);

    }
}
