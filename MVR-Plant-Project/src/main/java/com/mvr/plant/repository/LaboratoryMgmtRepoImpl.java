package com.mvr.plant.repository;

import com.mvr.plant.DTO.LabOperationDTO;
import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.LaboratoryOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LaboratoryMgmtRepoImpl implements ILaboratoryMgmtRepo {
    @Autowired
    private ILaboratoryRepo labOperationRepo;

    @Autowired
    private IPlantRepository plantRepo;


    @Override
    @Transactional
    public Map<String, Integer> updateLabOperationById(Long plantId, LaboratoryOperation labOp) {
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

        if (labOp.getCodReading1() != null) count++;
        if (labOp.getCodReading2() != null) count++;
        if (labOp.getCodReading3() != null) count++;
        if (labOp.getCodReading4() != null) count++;

        if (labOp.getBodReading1() != null) count++;
        if (labOp.getBodReading2() != null) count++;
        if (labOp.getBodReading3() != null) count++;
        if (labOp.getBodReading4() != null) count++;

        if (labOp.getThReading1() != null) count++;
        if (labOp.getThReading2() != null) count++;
        if (labOp.getThReading3() != null) count++;
        if (labOp.getThReading4() != null) count++;

        if (labOp.getTemperatureReading1() != null) count++;
        if (labOp.getTemperatureReading2() != null) count++;
        if (labOp.getTemperatureReading3() != null) count++;
        if (labOp.getTemperatureReading4() != null) count++;

        if (labOp.getTssReading1() != null) count++;
        if (labOp.getTssReading2() != null) count++;
        if (labOp.getTssReading3() != null) count++;
        if (labOp.getTssReading4() != null) count++;

        if (labOp.getPhReading1() != null) count++;
        if (labOp.getPhReading2() != null) count++;
        if (labOp.getPhReading3() != null) count++;
        if (labOp.getPhReading4() != null) count++;

        if (labOp.getFlowMeterReadingAm() != null) count++;
        if (labOp.getFlowMeterReadingPm() != null) count++; //26

        existing.setCodReading1(labOp.getCodReading1());
        existing.setCodReading2(labOp.getCodReading2());
        existing.setCodReading3(labOp.getCodReading3());
        existing.setCodReading4(labOp.getCodReading4());

        existing.setBodReading1(labOp.getBodReading1());
        existing.setBodReading2(labOp.getBodReading2());
        existing.setBodReading3(labOp.getBodReading3());
        existing.setBodReading4(labOp.getBodReading4());

        existing.setThReading1(labOp.getThReading1());
        existing.setThReading2(labOp.getThReading2());
        existing.setThReading3(labOp.getThReading3());
        existing.setThReading4(labOp.getThReading4());

        existing.setTemperatureReading1(labOp.getTemperatureReading1());
        existing.setTemperatureReading2(labOp.getTemperatureReading2());
        existing.setTemperatureReading3(labOp.getTemperatureReading3());
        existing.setTemperatureReading4(labOp.getTemperatureReading4());

        existing.setTssReading1(labOp.getTssReading1());
        existing.setTssReading2(labOp.getTssReading2());
        existing.setTssReading3(labOp.getTssReading3());
        existing.setTssReading4(labOp.getTssReading4());

        existing.setPhReading1(labOp.getPhReading1());
        existing.setPhReading2(labOp.getPhReading2());
        existing.setPhReading3(labOp.getPhReading3());
        existing.setPhReading4(labOp.getPhReading4());

        existing.setCod(average(
                labOp.getCodReading1(),
                labOp.getCodReading2(),
                labOp.getCodReading3(),
                labOp.getCodReading4()
        ));

        existing.setBod(average(
                labOp.getBodReading1(),
                labOp.getBodReading2(),
                labOp.getBodReading3(),
                labOp.getBodReading4()
        ));

        existing.setTn(average(
                labOp.getThReading1(),
                labOp.getThReading2(),
                labOp.getThReading3(),
                labOp.getThReading4()
        ));

        existing.setTemperature(average(
                labOp.getTemperatureReading1(),
                labOp.getTemperatureReading2(),
                labOp.getTemperatureReading3(),
                labOp.getTemperatureReading4()
        ));

        existing.setTss(average(
                labOp.getTssReading1(),
                labOp.getTssReading2(),
                labOp.getTssReading3(),
                labOp.getTssReading4()
        ));

        existing.setPh(average(
                labOp.getPhReading1(),
                labOp.getPhReading2(),
                labOp.getPhReading3(),
                labOp.getPhReading4()
        ));


        if (labOp.getFlowMeterReadingAm() != null && labOp.getFlowMeterReadingPm() != null) {
            existing.setCumulativeFlow(
                    (labOp.getFlowMeterReadingPm() - labOp.getFlowMeterReadingAm()) * 1000
            );
        } else {
            existing.setCumulativeFlow(null);
        }


        existing.setFlowMeterReadingAm(labOp.getFlowMeterReadingAm());
        existing.setFlowMeterReadingPm(labOp.getFlowMeterReadingPm());

        //save the update on db
        labOperationRepo.save(existing);

        Map<String, Integer> map = new HashMap<>();
        map.put("Lab Operation Details Updated Successfully", count);
        return map;
    }

    @Override
    public LaboratoryOperation getAllLabOperationDataByIdAndDate(Long plantId, LocalDate date) {
        return labOperationRepo.getLabOperationByPlantIdAndDate(plantId, date).orElse(null);
    }

    @Override
    public List<LabOperationDTO> getLabOperationsByDate(LocalDate date) {
        List<LaboratoryOperation> operations = labOperationRepo.getLabOperationsByDate(date);

        return operations.stream().map(op -> new LabOperationDTO(op.getPlant() != null ? op.getPlant().getPlantID() : null, op)).toList();
    }

    @Override
    public List<LabOperationDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate) {
        List<LaboratoryOperation> operations = labOperationRepo.findByOperationDateBetween(startDate, endDate);

        return operations.stream().map(op -> new LabOperationDTO(op.getPlant() != null ? op.getPlant().getPlantID() : null, op)).toList();
    }

    private Double average(Double... values) {
        double sum = 0;
        int count = 0;

        for (Double v : values) {
            if (v != null) {
                sum += v;
                count++;
            }
        }

        if (count == 0) return null;

        double avg = sum / count;

        // 🔥 ROUND TO 2 DECIMAL PLACES
        return Math.round(avg * 100.0) / 100.0;
    }

}
