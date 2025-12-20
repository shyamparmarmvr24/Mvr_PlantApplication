package com.mvr.plant.service;

import com.mvr.plant.DTO.VehicleOperationBetweenDTO;
import com.mvr.plant.DTO.VehicleOperationDTO;
import com.mvr.plant.entity.VehicleOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IVehicleOperationService
{
    public VehicleOperation getVehicleOperationByVehicleIdAndDate(Long vehicleID, LocalDate date);
    public VehicleOperation updateVehicleOperation(Long plantId, Long vehicleID, VehicleOperation vehicleOp);
    public List<VehicleOperationDTO> getVehicleOperationsByDate(LocalDate date);
    public List<VehicleOperationBetweenDTO> getVehicleOperationsBetween(LocalDate start, LocalDate end);
    public VehicleOperation getLatestFuelFilled(Long vehicleId);
}
