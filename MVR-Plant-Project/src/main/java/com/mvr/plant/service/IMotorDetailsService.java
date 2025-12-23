package com.mvr.plant.service;

import com.mvr.plant.entity.MotorDetails;

import java.util.List;

public interface IMotorDetailsService
{
    public MotorDetails createMotorDetails(Long plantId, MotorDetails motor);
    public MotorDetails updateMotorDetails(Long motorId,MotorDetails motor);
    public String deleteMotorDetails(Long motorId);
    public List<MotorDetails> getMotorDetailsByPlantId(Long plantId);
}
