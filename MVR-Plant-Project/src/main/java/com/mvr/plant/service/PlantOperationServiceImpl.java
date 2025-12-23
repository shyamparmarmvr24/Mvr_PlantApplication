package com.mvr.plant.service;

import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.repository.IPlantOperationMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PlantOperationServiceImpl implements IPlantOperationService {
    @Autowired
    private IPlantOperationMgmtRepo plantOpRepo;

    @Override
    public Map<String, Integer> updateOperationById(Long id, PlantOperation plantOp) {
        return plantOpRepo.updateOperationById(id, plantOp);
    }

    @Override
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date) {
        return plantOpRepo.getAllOperationDataByIdAndDate(plantId, date);
    }

    @Override
    public List<PlantOperationDTO> getAllOperationBydate(LocalDate date) {
        return plantOpRepo.getAllOperationBydate(date);
    }

    @Override
    public List<PlantOperationBetweenDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate) {
        return plantOpRepo.findByOperationDateBetween(startDate, endDate);
    }

    @Override
    public PlantOperation getLatestPowerBill(Long plantId) {
        return plantOpRepo.getLatestPowerBill(plantId);
    }

    @Override
    public PlantOperation getLatestWaterFilled(Long plantId) {
        return plantOpRepo.getLatestWaterFilled(plantId);
    }

}
