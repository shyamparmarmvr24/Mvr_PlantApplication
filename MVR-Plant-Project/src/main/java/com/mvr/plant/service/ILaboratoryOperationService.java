package com.mvr.plant.service;

import com.mvr.plant.DTO.LabOperationDTO;
import com.mvr.plant.entity.LaboratoryOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ILaboratoryOperationService
{
    public Map<String,Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp);
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date);
    public List<LabOperationDTO> getLabOperationsByDate(LocalDate date);
    public List<LabOperationDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);
}
