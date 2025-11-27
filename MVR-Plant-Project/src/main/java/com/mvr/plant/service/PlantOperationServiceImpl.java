package com.mvr.plant.service;

import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.repository.IPlantOperationMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PlantOperationServiceImpl implements IPlantOperationService
{
    @Autowired
    private IPlantOperationMgmtRepo plantOpRepo;

    @Override
    public Map<String, Integer> updateOperationById(Long id, PlantOperation plantOp) {
        return plantOpRepo.updateOperationById(id,plantOp);
    }

    @Override
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date) {
        return plantOpRepo.getAllOperationDataByIdAndDate(plantId, date);
    }

}
