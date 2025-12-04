package com.mvr.plant.service;

import com.mvr.plant.entity.VehicleOperation;

import java.time.LocalDate;
import java.util.Map;

public interface IVehicleOperationService
{
    public VehicleOperation getVehicleOperationByVehicleIdAndDate(Long vehicleID, LocalDate date);
    public Map<String,Integer> updateVehicleOperation(Long plantId, Long vehicleID, VehicleOperation vehicleOp);
}
