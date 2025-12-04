package com.mvr.plant.repository;

import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.Map;

public interface ILaboratoryMgmtRepo
{
    public Map<String,Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp);
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date);
}
