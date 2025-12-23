package com.mvr.plant.repository;

import com.mvr.plant.entity.PrivateVehicleDetails;
import com.mvr.plant.entity.VehicleInformation;

import java.time.LocalDate;
import java.util.List;

public interface IPrivateVehicleRepoImpl
{
    public PrivateVehicleDetails createPrivateVehicle(Long plantId, PrivateVehicleDetails privateVehicle);
    public List<PrivateVehicleDetails> getPrivateVehicleInformationByPlantIdAndDate(Long plantId, LocalDate date);
    public PrivateVehicleDetails updateVehicle(Long plantId, Long privateVehicleId, PrivateVehicleDetails updated);
    public void deletePrivateVehicle(Long privateVehicleId);
}
