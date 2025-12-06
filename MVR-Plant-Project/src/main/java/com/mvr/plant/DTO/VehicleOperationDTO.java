package com.mvr.plant.DTO;

import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.entity.VehicleOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleOperationDTO
{
    private Long plantId;
    private VehicleInformation vehicle;
    private VehicleOperation vehicleOp;
}
