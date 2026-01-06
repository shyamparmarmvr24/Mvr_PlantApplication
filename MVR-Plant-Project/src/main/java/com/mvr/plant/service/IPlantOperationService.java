package com.mvr.plant.service;

import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.DTO.PowerBillDTO;
import com.mvr.plant.DTO.WaterDTO;
import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPlantOperationService
{
    public Map<String, Integer> updateOperationById(Long plantId, PlantOperation plantOp);
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date);
    public List<PlantOperationDTO> getAllOperationBydate(LocalDate date);
    public List<PlantOperationBetweenDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);
    public PowerBillDTO getLatestPowerBill(Long plantId, LocalDate date);
    public WaterDTO getLatestWaterFilled(Long plantId, LocalDate date);
    public List<WaterDTO> getWaterDetailsByPlantId(Long plantId);
    public List<PowerBillDTO> getPowerBillDetailsByPlantId(Long plantId);
}
