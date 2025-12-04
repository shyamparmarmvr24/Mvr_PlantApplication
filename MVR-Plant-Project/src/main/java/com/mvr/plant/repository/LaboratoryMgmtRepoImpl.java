package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Repository
public class LaboratoryMgmtRepoImpl implements ILaboratoryMgmtRepo
{
    @Autowired
    private ILaboratoryRepo labOperationRepo;

    @Autowired
    private IPlantRepository plantRepo;


    @Override
    @Transactional
    public Map<String, Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp)
    {
        LocalDate date = labOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // Find existing operation for plant + date
        LaboratoryOperation exist = labOperationRepo
                .getLabOperationByPlantIdAndDate(plantId, date)
                .orElse(null);

        // If no existing operation → CREATE NEW ONE
        if (exist == null) {
            // First fetch the parent plant
            FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
                    .orElseThrow(() -> new IllegalStateException("Parent plant not found"));

            // Set parent reference and save new operation (for that date)
            labOp.setPlant(plant);
            labOperationRepo.save(labOp);
        }

        // existing operation by plant + date
        LaboratoryOperation existing = labOperationRepo
                .getLabOperationByPlantIdAndDate(plantId, date)
                .orElseThrow(() -> new IllegalStateException("Lab Operation Not Found For Date"));

        int count = 0;

        if (labOp.getCod() != null) count++;
        if (labOp.getBod() != null) count++;
        if (labOp.getTh() != null) count++;
        if (labOp.getTemperature() != null) count++;
        if (labOp.getTss() != null) count++;
        if (labOp.getPh() != null) count++;
        if (labOp.getVelocity() != null) count++;
        if (labOp.getCumulativeFlow() != null) count++;
        if (labOp.getFlowMeterReadingAm() != null) count++;
        if (labOp.getFlowMeterReadingPm() != null) count++; //10




        existing.setCod(labOp.getCod());
        existing.setBod(labOp.getBod());
        existing.setTh(labOp.getTh());
        existing.setTemperature(labOp.getTemperature());
        existing.setTss(labOp.getTss());
        existing.setPh(labOp.getPh());
        existing.setVelocity(labOp.getVelocity());
        existing.setCumulativeFlow(labOp.getCumulativeFlow());
        existing.setFlowMeterReadingAm(labOp.getFlowMeterReadingAm());
        existing.setFlowMeterReadingPm(labOp.getFlowMeterReadingPm());



        //save the update on db
        labOperationRepo.save(existing);

        Map<String, Integer> map = new HashMap<>();
        map.put("Lab Operation Details Updated Successfully", count);
        return map;
    }

    @Override
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date)
    {
        return labOperationRepo.getLabOperationByPlantIdAndDate(plantId,date).orElse(null);
    }
}
