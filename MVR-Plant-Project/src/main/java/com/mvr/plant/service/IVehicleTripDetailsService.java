package com.mvr.plant.service;

import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleTripDetails;

import java.util.List;

public interface IVehicleTripDetailsService
{
    public VehicleTripDetails updateVehicle(Long vehicleOpId, VehicleTripDetails tripDet);
    public List<VehicleTripDetailsDTO> getVehicleTripDetails(Long vehicleOpId);
}
