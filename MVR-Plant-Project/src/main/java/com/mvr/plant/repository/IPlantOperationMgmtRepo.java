package com.mvr.plant.repository;

import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPlantOperationMgmtRepo
{
    public Map<String,Integer> updateOperationById(Long id, PlantOperation plantOp);
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date);
    public List<PlantOperationDTO> getAllOperationBydate(LocalDate date);
    public  List<PlantOperationBetweenDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);
    public void recomputePlantTotals(Long plantId, LocalDate date);
}
