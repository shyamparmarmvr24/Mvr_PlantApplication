package com.mvr.plant.service;

import com.mvr.plant.entity.MotorDetails;
import com.mvr.plant.repository.IMotorDetailsRepoMgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorServiceImpl implements IMotorDetailsService
{
    @Autowired
    private IMotorDetailsRepoMgmt motorRepo;

    @Override
    public MotorDetails createMotorDetails(Long plantId, MotorDetails motor) {
        return motorRepo.createMotorDetails(plantId,motor);
    }

    @Override
    public MotorDetails updateMotorDetails(Long motorId, MotorDetails motor) {
        return motorRepo.updateMotorDetails(motorId,motor);
    }

    @Override
    public String deleteMotorDetails(Long motorId) {
        return motorRepo.deleteMotorDetails(motorId);
    }

    @Override
    public List<MotorDetails> getMotorDetailsByPlantId(Long plantId) {
        return motorRepo.getMotorDetailsByPlantId(plantId);
    }
}
