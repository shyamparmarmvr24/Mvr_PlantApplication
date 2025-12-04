package com.mvr.plant.service;

import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.repository.IVehicleRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements IVehicleService
{
    @Autowired
    private IVehicleRepoImpl vehicleRepo;

    @Override
    public VehicleInformation createVehicle(Long plantId, VehicleInformation vehicle) {
        return vehicleRepo.createVehicle(plantId,vehicle);
    }

    @Override
    public List<VehicleInformation> getVehicleInformationByPlantId(Long plantId) {
        return vehicleRepo.getVehicleInformationByPlantId(plantId);
    }

    @Override
    public VehicleInformation updateVehicle(Long plantId, Long vehicleId, VehicleInformation vehicle) {
        return vehicleRepo.updateVehicle(plantId,vehicleId,vehicle);
    }
}
