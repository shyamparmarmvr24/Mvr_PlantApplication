package com.mvr.plant.service;

import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.Map;

public interface IPlantOperationService
{
    public Map<String, Integer> updateOperationById(Long plantId, PlantOperation plantOp);
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date);
}
