package com.mvr.plant.repository;

import com.mvr.plant.entity.DgDetails;
import com.mvr.plant.entity.MotorDetails;

import java.util.List;

public interface IMotorDetailsRepoMgmt
{
    public MotorDetails createMotorDetails(Long plantId, MotorDetails motor);
    public MotorDetails updateMotorDetails(Long motorId,MotorDetails motor);
    public String deleteMotorDetails(Long motorId);
    public List<MotorDetails> getMotorDetailsByPlantId(Long plantId);
}
