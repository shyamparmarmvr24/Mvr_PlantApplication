package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.VehicleInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class VehichleRepoMgmtImpl implements IVehicleRepoImpl
{
    @Autowired
    private IVehicleRepo vehicleRepo;

    @Autowired
    private IPlantRepository plantRepo;


    @Override
    public VehicleInformation createVehicle(Long plantId, VehicleInformation vehicle)
    {

        // Fetch parent plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
                .orElseThrow(() -> new IllegalStateException("Plant not found: " + plantId));

        // Set FK reference
        vehicle.setPlant(plant);

        // Save entity
        return vehicleRepo.save(vehicle);
    }

    @Override
    public List<VehicleInformation> getVehicleInformationByPlantId(Long plantId) {
        return vehicleRepo.getVehicleInformationByPlantId(plantId);
    }

    @Override
    public VehicleInformation updateVehicle(Long plantId, Long vehicleId, VehicleInformation updated) {

        // Fetch existing vehicle + verify it belongs to the plant
        VehicleInformation existing = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found: " + vehicleId));

        if (!existing.getPlant().getPlantID().equals(plantId)) {
            throw new IllegalStateException("Vehicle does not belong to plant: " + plantId);
        }

        // Update fields only if provided (null-safe update)
        if (updated.getVehicleModelName() != null)
            existing.setVehicleModelName(updated.getVehicleModelName());

        if (updated.getVehicleNumber() != null)
            existing.setVehicleNumber(updated.getVehicleNumber());

        if (updated.getVehicleChassisNo() != null)
            existing.setVehicleChassisNo(updated.getVehicleChassisNo());

        if (updated.getDateOfRegistration() != null)
            existing.setDateOfRegistration(updated.getDateOfRegistration());

        if (updated.getInsuranceDate() != null)
            existing.setInsuranceDate(updated.getInsuranceDate());

        if (updated.getInsuranceExpiryDate() != null)
            existing.setInsuranceExpiryDate(updated.getInsuranceExpiryDate());

        return vehicleRepo.save(existing);
    }
}
