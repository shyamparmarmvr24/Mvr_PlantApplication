package com.mvr.plant.service;

import com.mvr.plant.DTO.VehicleOperationBetweenDTO;
import com.mvr.plant.DTO.VehicleOperationDTO;
import com.mvr.plant.entity.VehicleOperation;
import com.mvr.plant.repository.IVehicleOperationRepoMgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class VehicleOperationServiceImpl implements IVehicleOperationService
{
    @Autowired
    private IVehicleOperationRepoMgmt vehicleOpRepo;

    @Override
    public VehicleOperation getVehicleOperationByVehicleIdAndDate(Long vehicleID, LocalDate date) {
        return vehicleOpRepo.getVehicleOperationByVehicleIdAndDate(vehicleID,date);
    }

    @Override
    public Map<String, Integer> updateVehicleOperation(Long plantId, Long vehicleID, VehicleOperation vehicleOp) {
        return vehicleOpRepo.updateVehicleOperation(plantId,vehicleID,vehicleOp);
    }

    @Override
    public List<VehicleOperationDTO> getVehicleOperationsByDate(LocalDate date) {
        return vehicleOpRepo.getVehicleOperationsByDate(date);
    }

    @Override
    public List<VehicleOperationBetweenDTO> getVehicleOperationsBetween(LocalDate start, LocalDate end) {
        return vehicleOpRepo.getVehicleOperationsBetween(start,end);
    }
}
