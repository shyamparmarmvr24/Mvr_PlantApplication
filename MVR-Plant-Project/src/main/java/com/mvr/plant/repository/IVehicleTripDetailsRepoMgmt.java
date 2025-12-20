package com.mvr.plant.repository;

import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleTripDetails;

import java.util.List;

public interface IVehicleTripDetailsRepoMgmt
{
    public VehicleTripDetails updateVehicle(Long vehicleOpId,VehicleTripDetails tripsDet);
    public List<VehicleTripDetailsDTO> getVehicleTripDetails(Long vehicleOpId);
}
