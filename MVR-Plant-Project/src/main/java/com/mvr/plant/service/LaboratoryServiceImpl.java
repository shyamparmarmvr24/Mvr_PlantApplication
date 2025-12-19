package com.mvr.plant.service;

import com.mvr.plant.DTO.LabOperationDTO;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.repository.ILaboratoryMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class LaboratoryServiceImpl implements ILaboratoryOperationService
{
    @Autowired
    private ILaboratoryMgmtRepo labRepo;

    @Override
    public Map<String, Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp) {
        return labRepo.updateLabOperationById(plantId,labOp);
    }

    @Override
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date) {
        return labRepo.getAllLabOperationDataByIdAndDate(plantId,date);
    }

    @Override
    public List<LabOperationDTO> getLabOperationsByDate(LocalDate date) {
        return labRepo.getLabOperationsByDate(date);
    }
}
