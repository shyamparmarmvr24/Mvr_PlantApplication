package com.mvr.plant.repository;

import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.Map;

public interface IPlantOperationMgmtRepo
{
    public Map<String,Integer> updateOperationById(Long id, PlantOperation plantOp);
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date);
}
