package com.mvr.plant.repository;

import com.mvr.plant.entity.VehicleInformation;

import java.util.List;

public interface IVehicleRepoImpl
{
    public VehicleInformation createVehicle(Long plantId, VehicleInformation vehicle);
    public List<VehicleInformation> getVehicleInformationByPlantId(Long plantId);
    public VehicleInformation updateVehicle(Long plantId, Long vehicleId, VehicleInformation vehicle);

}
