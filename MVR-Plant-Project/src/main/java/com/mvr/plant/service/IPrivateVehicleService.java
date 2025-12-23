package com.mvr.plant.service;

import com.mvr.plant.entity.PrivateVehicleDetails;

import java.time.LocalDate;
import java.util.List;

public interface IPrivateVehicleService
{
    public PrivateVehicleDetails createPrivateVehicle(Long plantId, PrivateVehicleDetails privateVehicle);
    public List<PrivateVehicleDetails> getPrivateVehicleInformationByPlantIdAndDate(Long plantId, LocalDate date);
    public PrivateVehicleDetails updateVehicle(Long plantId, Long privateVehicleId, PrivateVehicleDetails updated);
    public void deletePrivateVehicle(Long privateVehicleId);
}
