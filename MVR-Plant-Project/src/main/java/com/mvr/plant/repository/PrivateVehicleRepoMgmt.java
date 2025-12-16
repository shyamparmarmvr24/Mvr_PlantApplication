package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PrivateVehicleDetails;
import com.mvr.plant.entity.VehicleInformation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class PrivateVehicleRepoMgmt implements IPrivateVehicleRepoImpl
{
    @Autowired
    private IPrivateVehicleRepo privateVehRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Override
    public PrivateVehicleDetails createPrivateVehicle(Long plantId, PrivateVehicleDetails privateVehicle)
    {
        // Fetch parent plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
                .orElseThrow(() -> new IllegalStateException("Plant not found: " + plantId));

        // Set FK reference
        privateVehicle.setPlant(plant);

        // ✅ ALWAYS REQUIRE DATE
        if (privateVehicle.getOperationDate() == null) {
            throw new IllegalArgumentException("Operation date is required");
        }

        // Save entity
        return privateVehRepo.save(privateVehicle);
    }

    @Override
    public List<PrivateVehicleDetails> getPrivateVehicleInformationByPlantIdAndDate(Long plantId, LocalDate date)
    {
        return privateVehRepo.getPrivateVehicleByPlantIdAndDate(plantId,date);
    }

    @Override
    public PrivateVehicleDetails updateVehicle(Long plantId, Long privateVehicleId, PrivateVehicleDetails updated)
    {
        // Fetch existing vehicle + verify it belongs to the plant
        PrivateVehicleDetails existing = privateVehRepo.findById(privateVehicleId)
                .orElseThrow(() -> new IllegalStateException("Private Vehicle not found: " + privateVehicleId));

        if (!existing.getPlant().getPlantID().equals(plantId)) {
            throw new IllegalStateException("Vehicle does not belong to plant: " + plantId);
        }

        existing.setPrivateVehNumber(updated.getPrivateVehNumber());
        existing.setDriverNumber(updated.getDriverNumber());
        existing.setDriverName(updated.getDriverName());
        existing.setReceivedSludgeLtrs(updated.getReceivedSludgeLtrs());
        existing.setReceivedSludgeKgs(updated.getReceivedSludgeKgs());
        // 🔴 THIS WAS MISSING
        existing.setOperationDate(updated.getOperationDate());
        return privateVehRepo.save(existing);
    }
}
