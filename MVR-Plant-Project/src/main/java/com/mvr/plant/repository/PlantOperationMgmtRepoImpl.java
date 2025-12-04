package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PlantOperationMgmtRepoImpl implements IPlantOperationMgmtRepo
{
    @Autowired
    private IPlantOperationRepository operationRepo;

    @Autowired
    private IPlantRepository plantRepo;


    @Override
    @Transactional
    public Map<String, Integer> updateOperationById(Long plantId, PlantOperation plantOp)
    {

    LocalDate date = plantOp.getOperationDate();
    if (date == null) {
        throw new IllegalStateException("operationDate is required");
    }

    // Find existing operation for plant + date
    PlantOperation exist = operationRepo
            .getOperationByPlantIdAndDate(plantId, date)
            .orElse(null);

    // If no existing operation → CREATE NEW ONE
    if (exist == null) {
        // First fetch the parent plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
                .orElseThrow(() -> new IllegalStateException("Parent plant not found"));

        // Set parent reference and save new operation (for that date)
        plantOp.setPlant(plant);
        operationRepo.save(plantOp);
    }

    // existing operation by plant + date
    PlantOperation existing = operationRepo
            .getOperationByPlantIdAndDate(plantId, date)
            .orElseThrow(() -> new IllegalStateException("Plant Operation Not Found For Date"));

    int count = 0;

        if (plantOp.getSludgeTankLevelAm() != null) count++;
        if (plantOp.getSludgeTankLevelPm() != null) count++;
        if (plantOp.getSludgeReceived() != null) count++;
        if (plantOp.getNoOfTrips() != null) count++;
        if (plantOp.getSludgeProcessed() != null) count++;
        if (plantOp.getBiocharProduced() != null) count++;
        if (plantOp.getPlantRunningHrs() != null) count++;
        if (plantOp.getSolarIntegration() != null) count++;
        if (plantOp.getPowerReadingAmImport() != null) count++;
        if (plantOp.getPowerReadingAmExport() != null) count++;
        if (plantOp.getPowerReadingPmImport() != null) count++;
        if (plantOp.getPowerReadingPmExport() != null) count++;
        if (plantOp.getPowerReadingAm() != null) count++;
        if (plantOp.getPowerReadingPm() != null) count++;
        if (plantOp.getDgReadingAm() != null) count++;
        if (plantOp.getDgReadingPm() != null) count++;
        if (plantOp.getDgDiesalPercentageAm() != null) count++;
        if (plantOp.getDgDiesalPercentagePm() != null) count++;
        if (plantOp.getVehicleReadingAm() != null) count++;
        if (plantOp.getVehicleReadingPm() != null) count++;
        if (plantOp.getVehicleFuelLevel() != null) count++;
        if (plantOp.getPolymerUsage() != null) count++;
        if (plantOp.getPillets() != null) count++;  //23


        existing.setSludgeTankLevelAm(plantOp.getSludgeTankLevelAm());
        existing.setSludgeTankLevelPm(plantOp.getSludgeTankLevelPm());
        existing.setSludgeReceived(plantOp.getSludgeReceived());
        existing.setNoOfTrips(plantOp.getNoOfTrips());
        existing.setSludgeProcessed(plantOp.getSludgeProcessed());
        existing.setBiocharProduced(plantOp.getBiocharProduced());
        existing.setPlantRunningHrs(plantOp.getPlantRunningHrs());
        existing.setSolarIntegration(plantOp.getSolarIntegration());
        existing.setPowerReadingAmImport(plantOp.getPowerReadingAmImport());
        existing.setPowerReadingAmExport(plantOp.getPowerReadingAmExport());
        existing.setPowerReadingPmImport(plantOp.getPowerReadingPmImport());
        existing.setPowerReadingPmExport(plantOp.getPowerReadingPmExport());
        existing.setPowerReadingAm(plantOp.getPowerReadingAm());
        existing.setPowerReadingPm(plantOp.getPowerReadingPm());
        if (plantOp.getPowerReadingAm() != null && plantOp.getPowerReadingPm() != null) {
            existing.setPowerConsumed(Math.abs(
                    plantOp.getPowerReadingAm() - plantOp.getPowerReadingPm()
            ));
        }
        else
        {
            existing.setPowerConsumed(0.0);
        }
        existing.setDgReadingAm(plantOp.getDgReadingAm());
        existing.setDgReadingPm(plantOp.getDgReadingPm());
        existing.setDgDiesalPercentageAm(plantOp.getDgDiesalPercentageAm());
        existing.setDgDiesalPercentagePm(plantOp.getDgDiesalPercentagePm());
        if (plantOp.getDgReadingAm() != null && plantOp.getDgReadingPm() != null) {
            existing.setDgRunHours(Math.abs(
                    plantOp.getDgReadingAm() - plantOp.getDgReadingPm()
            ));
        }
        else
        {
            existing.setDgRunHours(0.0);
        }
        existing.setVehicleReadingAm(plantOp.getVehicleReadingAm());
        existing.setVehicleReadingPm(plantOp.getVehicleReadingPm());
        existing.setVehicleFuelLevel(plantOp.getVehicleFuelLevel());
        existing.setPolymerUsage(plantOp.getPolymerUsage());
        existing.setPillets(plantOp.getPillets());
        existing.setRemarks(plantOp.getRemarks());
       //save the update on db
       operationRepo.save(existing);

       Map<String, Integer> map = new HashMap<>();
       map.put("Operation Details Updated Successfully", count);
       return map;
       }

      //getting date wise operation from db
      @Override
      public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date)
      {
        return operationRepo.getOperationByPlantIdAndDate(plantId, date)
                .orElse(null);
      }

}

