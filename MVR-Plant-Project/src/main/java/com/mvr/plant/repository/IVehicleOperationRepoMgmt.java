package com.mvr.plant.repository;

import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.entity.VehicleOperation;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IVehicleOperationRepoMgmt
{
    public VehicleOperation getVehicleOperationByVehicleIdAndDate(Long vehicleID,LocalDate date);
    public Map<String,Integer> updateVehicleOperation(Long plantId, Long vehicleID, VehicleOperation vehicleOp);
}
