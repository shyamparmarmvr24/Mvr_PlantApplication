package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.MotorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MotorDetailsRepoImpl implements IMotorDetailsRepoMgmt
{
    @Autowired
    private IMotorDetailsRepo motorRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Override
    public MotorDetails createMotorDetails(Long plantId, MotorDetails motor)
    {
        // Fetch Plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId).orElseThrow(() -> new RuntimeException("Plant not found: " + plantId));

        // Set FK plant reference
        motor.setPlant(plant);

        // Save employee
        return motorRepo.save(motor);
    }

    @Override
    public MotorDetails updateMotorDetails(Long motorId, MotorDetails motor)
    {
        MotorDetails existing = motorRepo.findById(motorId).orElseThrow(() -> new IllegalStateException("Motor Details Not Found"));

        existing.setMotorModelName(motor.getMotorModelName());
        existing.setMotorType(motor.getMotorType());
        existing.setMotorSerialNo(motor.getMotorSerialNo());
        existing.setMotorMake(motor.getMotorMake());

        return motorRepo.save(existing);
    }

    @Override
    public String deleteMotorDetails(Long motorId) {
        Optional<MotorDetails> byId = motorRepo.findById(motorId);
        if(byId.isPresent())
        {
            motorRepo.deleteById(motorId);
            return "Motor Details Deleted Successfully";
        }
        return "Motor Details Not Found";
    }

    @Override
    public List<MotorDetails> getMotorDetailsByPlantId(Long plantId) {
        return motorRepo.getMotorDetailsByPlantId(plantId);
    }
}
