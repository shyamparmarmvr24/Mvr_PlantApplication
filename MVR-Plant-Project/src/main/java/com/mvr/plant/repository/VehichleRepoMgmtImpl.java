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

        if (updated.getGpsStatus() != null)
            existing.setGpsStatus(updated.getGpsStatus());

        if (updated.getGpsInstallationDate() != null)
            existing.setGpsInstallationDate(updated.getGpsInstallationDate());

        if (updated.getVehicleEngineNumber() != null)
            existing.setVehicleEngineNumber(updated.getVehicleEngineNumber());

        if (updated.getVehicleBatteryMake() != null)
            existing.setVehicleBatteryMake(updated.getVehicleBatteryMake());

        if (updated.getVehicleBatteryNumber() != null)
            existing.setVehicleBatteryNumber(updated.getVehicleBatteryNumber());

        if (updated.getVehicleBatteryPurchaseDate() != null)
            existing.setVehicleBatteryPurchaseDate(updated.getVehicleBatteryPurchaseDate());

        if (updated.getVehicleBatteryExpiryDate() != null)
            existing.setVehicleBatteryExpiryDate(updated.getVehicleBatteryExpiryDate());

        if (updated.getVehicleTyreFrontRightMake() != null)
            existing.setVehicleTyreFrontRightMake(updated.getVehicleTyreFrontRightMake());

        if (updated.getVehicleTyreFrontRightSerialNo() != null)
            existing.setVehicleTyreFrontRightSerialNo(updated.getVehicleTyreFrontRightSerialNo());

        if (updated.getVehicleTyreFrontLeftMake() != null)
            existing.setVehicleTyreFrontLeftMake(updated.getVehicleTyreFrontLeftMake());

        if (updated.getVehicleTyreFrontLeftSerialNo() != null)
            existing.setVehicleTyreFrontLeftSerialNo(updated.getVehicleTyreFrontLeftSerialNo());

        if (updated.getVehicleTyreRearRightInnerMake() != null)
            existing.setVehicleTyreRearRightInnerMake(updated.getVehicleTyreRearRightInnerMake());

        if (updated.getVehicleTyreRearRightInnerSerialNo() != null)
            existing.setVehicleTyreRearRightInnerSerialNo(updated.getVehicleTyreRearRightInnerSerialNo());

        if (updated.getVehicleTyreRearRightOuterMake() != null)
            existing.setVehicleTyreRearRightOuterMake(updated.getVehicleTyreRearRightOuterMake());

        if (updated.getVehicleTyreRearRightOuterSerialNo() != null)
            existing.setVehicleTyreRearRightOuterSerialNo(updated.getVehicleTyreRearRightOuterSerialNo());

        if (updated.getVehicleTyreRearLeftInnerMake() != null)
            existing.setVehicleTyreRearLeftInnerMake(updated.getVehicleTyreRearLeftInnerMake());

        if (updated.getVehicleTyreRearLeftInnerSerialNo() != null)
            existing.setVehicleTyreRearLeftInnerSerialNo(updated.getVehicleTyreRearLeftInnerSerialNo());

        if (updated.getVehicleTyreRearLeftOuterMake() != null)
            existing.setVehicleTyreRearLeftOuterMake(updated.getVehicleTyreRearLeftOuterMake());

        if (updated.getVehicleTyreRearLeftOuterSerialNo() != null)
            existing.setVehicleTyreRearLeftOuterSerialNo(updated.getVehicleTyreRearLeftOuterSerialNo());

        if (updated.getStepneyMake() != null)
            existing.setStepneyMake(updated.getStepneyMake());

        if (updated.getStepneySerialNo() != null)
            existing.setStepneySerialNo(updated.getStepneySerialNo());

        return vehicleRepo.save(existing);
    }
}
