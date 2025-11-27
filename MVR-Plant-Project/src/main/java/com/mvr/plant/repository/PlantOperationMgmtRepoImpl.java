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

    //for button logics it gives update count
    if (plantOp.getSludgeTankLevel() != null) count++;
    if (plantOp.getSludgeProcessed() != null) count++;
    if (plantOp.getSludgeReceived() != null) count++;
    if (plantOp.getBiocharProduced() != null) count++;
    if (plantOp.getPlantRunningHrs() != null) count++;

    if (plantOp.getSolarReadingAm() != null) count++;
    if (plantOp.getSolarReadingPm() != null) count++;

    if (plantOp.getPowerReadingAm() != null) count++;
    if (plantOp.getPowerReadingPm() != null) count++;

    if (plantOp.getNoOfTrips() != null) count++;
    if (plantOp.getVehicleReadingAm() != null) count++;
    if (plantOp.getVehicleReadingPm() != null) count++;

    if (plantOp.getDgUsed() != null) count++;
    if (plantOp.getDgRunHrs() != null) count++;

    //set the existing object for update
    existing.setSludgeTankLevel(plantOp.getSludgeTankLevel());
    existing.setSludgeProcessed(plantOp.getSludgeProcessed());
    existing.setSludgeReceived(plantOp.getSludgeReceived());
    existing.setBiocharProduced(plantOp.getBiocharProduced());
    existing.setPlantRunningHrs(plantOp.getPlantRunningHrs());

    existing.setSolarReadingAm(plantOp.getSolarReadingAm());
    existing.setSolarReadingPm(plantOp.getSolarReadingPm());

    existing.setPowerReadingAm(plantOp.getPowerReadingAm());
    existing.setPowerReadingPm(plantOp.getPowerReadingPm());

    existing.setNoOfTrips(plantOp.getNoOfTrips());
    existing.setVehicleReadingAm(plantOp.getVehicleReadingAm());
    existing.setVehicleReadingPm(plantOp.getVehicleReadingPm());

    existing.setDgUsed(plantOp.getDgUsed());
    existing.setDgRunHrs(plantOp.getDgRunHrs());

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


//    @Override
//    @Transactional
//    public Map<String, Integer> updateOperationById(Long plantId, PlantOperation plantOp)
//    {
//
//        PlantOperation exist = operationRepo
//                .getOperationByPlantId(plantId)
//                .orElse(null);
//
//        // If no existing operation → CREATE NEW ONE
//        if(exist == null) {
//            // First fetch the parent plant
//            FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
//                    .orElseThrow(() -> new IllegalStateException("Parent plant not found"));
//
//            // Set parent reference
//            plantOp.setPlant(plant);
//
//            // Insert new operation
//            operationRepo.save(plantOp);
//        }
//
//
//        // Fetch existing operation by plantId
//        PlantOperation existing = operationRepo
//                .getOperationByPlantId(plantId)
//                .orElseThrow(() -> new IllegalStateException("Plant Id Not Found"));
//
//        int count = 0;
//
//        // Check each of the 14 updatable fields for non-null in the incoming object
//        if (plantOp.getSludgeTankLevel() != null) count++;
//        if (plantOp.getSludgeProcessed() != null) count++;
//        if (plantOp.getSludgeReceived() != null) count++;
//        if (plantOp.getBiocharProduced() != null) count++;
//        if (plantOp.getPlantRunningHrs() != null) count++;
//
//        if (plantOp.getSolarReadingAm() != null) count++;
//        if (plantOp.getSolarReadingPm() != null) count++;
//
//        if (plantOp.getPowerReadingAm() != null) count++;
//        if (plantOp.getPowerReadingPm() != null) count++;
//
//        if (plantOp.getNoOfTrips() != null) count++;
//        if (plantOp.getVehicleReadingAm() != null) count++;
//        if (plantOp.getVehicleReadingPm() != null) count++;
//
//        if (plantOp.getDgUsed() != null) count++;
//        if (plantOp.getDgRunHrs() != null) count++;
//
//
//        if (plantOp.getSludgeTankLevel() != null) existing.setSludgeTankLevel(plantOp.getSludgeTankLevel());
//        if (plantOp.getSludgeProcessed() != null) existing.setSludgeProcessed(plantOp.getSludgeProcessed());
//        if (plantOp.getSludgeReceived() != null) existing.setSludgeReceived(plantOp.getSludgeReceived());
//        if (plantOp.getBiocharProduced() != null) existing.setBiocharProduced(plantOp.getBiocharProduced());
//        if (plantOp.getPlantRunningHrs() != null) existing.setPlantRunningHrs(plantOp.getPlantRunningHrs());

//        if (plantOp.getSolarReadingAm() != null) existing.setSolarReadingAm(plantOp.getSolarReadingAm());
//        if (plantOp.getSolarReadingPm() != null) existing.setSolarReadingPm(plantOp.getSolarReadingPm());
//
//        if (plantOp.getPowerReadingAm() != null) existing.setPowerReadingAm(plantOp.getPowerReadingAm());
//        if (plantOp.getPowerReadingPm() != null) existing.setPowerReadingPm(plantOp.getPowerReadingPm());

//        if (plantOp.getNoOfTrips() != null) existing.setNoOfTrips(plantOp.getNoOfTrips());
//        if (plantOp.getVehicleReadingAm() != null) existing.setVehicleReadingAm(plantOp.getVehicleReadingAm());
//        if (plantOp.getVehicleReadingPm() != null) existing.setVehicleReadingPm(plantOp.getVehicleReadingPm());
//
//        if (plantOp.getDgUsed() != null) existing.setDgUsed(plantOp.getDgUsed());
//        if(plantOp.getDgRunHrs() != null) existing.setDgRunHrs(plantOp.getDgRunHrs());
//
//        existing.setSludgeTankLevel(plantOp.getSludgeTankLevel());
//        existing.setSludgeProcessed(plantOp.getSludgeProcessed());
//        existing.setSludgeReceived(plantOp.getSludgeReceived());
//        existing.setBiocharProduced(plantOp.getBiocharProduced());
//        existing.setPlantRunningHrs(plantOp.getPlantRunningHrs());
//
//        existing.setSolarReadingAm(plantOp.getSolarReadingAm());
//        existing.setSolarReadingPm(plantOp.getSolarReadingPm());
//
//        existing.setPowerReadingAm(plantOp.getPowerReadingAm());
//        existing.setPowerReadingPm(plantOp.getPowerReadingPm());
//
//        existing.setNoOfTrips(plantOp.getNoOfTrips());
//        existing.setVehicleReadingAm(plantOp.getVehicleReadingAm());
//        existing.setVehicleReadingPm(plantOp.getVehicleReadingPm());
//
//        existing.setDgUsed(plantOp.getDgUsed());
//        existing.setDgRunHrs(plantOp.getDgRunHrs());
//
//        // Save updated operation
//        operationRepo.save(existing);
//
//        // Prepare result
//        Map<String, Integer> map = new HashMap<>();
//        map.put("Operation Details Updated Successfully", count);
//        return map;
//    }
//
//
//
//    @Override
//    public PlantOperation getAllOperationDataById(Long id)
//    {
//        PlantOperation plantOp= operationRepo.getOperationByPlantId(id).orElseThrow(() -> new IllegalStateException("Plant Operation Not Found"));
//         return plantOp;
//    }
