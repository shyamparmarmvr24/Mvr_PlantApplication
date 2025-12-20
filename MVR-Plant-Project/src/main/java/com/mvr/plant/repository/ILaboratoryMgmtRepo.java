package com.mvr.plant.repository;

import com.mvr.plant.DTO.LabOperationDTO;
import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ILaboratoryMgmtRepo
{
    public Map<String,Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp);
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date);
    public List<LabOperationDTO> getLabOperationsByDate(LocalDate date);
    public  List<LabOperationDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);
}
