package com.mvr.plant.service;

import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.DTO.PowerBillDTO;
import com.mvr.plant.DTO.WaterDTO;
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
    public PowerBillDTO getLatestPowerBill(Long plantId, LocalDate date) {
        return plantOpRepo.getLatestPowerBill(plantId,date);
    }

    @Override
    public WaterDTO getLatestWaterFilled(Long plantId, LocalDate date) {
        return plantOpRepo.getLatestWaterFilled(plantId,date);
    }

    @Override
    public List<WaterDTO> getWaterDetailsByPlantId(Long plantId) {
        return plantOpRepo.getWaterDetailsByPlantId(plantId);
    }

    @Override
    public List<PowerBillDTO> getPowerBillDetailsByPlantId(Long plantId) {
        return plantOpRepo.getPowerBillDetailsByPlantId(plantId);
    }


}
