package com.mvr.plant.service;

import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleTripDetails;
import com.mvr.plant.repository.IVehicleTripDetailsRepoMgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTripDetailServiceImpl implements IVehicleTripDetailsService {
    @Autowired
    private IVehicleTripDetailsRepoMgmt vehicleTripRepo;

    @Override
    public VehicleTripDetails updateVehicle(Long vehicleOpId, VehicleTripDetails tripDet) {
        return vehicleTripRepo.updateVehicle(vehicleOpId, tripDet);
    }

    @Override
    public List<VehicleTripDetailsDTO> getVehicleTripDetails(Long vehicleOpId) {
        return vehicleTripRepo.getVehicleTripDetails(vehicleOpId);
    }

    @Override
    public String deleteTrip(Long tripId) {
        return vehicleTripRepo.deleteTrip(tripId);
    }
}
