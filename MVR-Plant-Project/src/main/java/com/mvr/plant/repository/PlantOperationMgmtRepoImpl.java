package com.mvr.plant.repository;

import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.DTO.PowerBillDTO;
import com.mvr.plant.DTO.WaterDTO;
import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.entity.VehicleOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlantOperationMgmtRepoImpl implements IPlantOperationMgmtRepo {
    @Autowired
    private IPlantOperationRepository operationRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Autowired
    private IVehicleOperationRepo vehicleOpRepo;

    @Override
    @Transactional
    public Map<String, Integer> updateOperationById(Long plantId, PlantOperation plantOp) {

        LocalDate date = plantOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // Find existing operation for plant + date
        PlantOperation exist = operationRepo.getOperationByPlantIdAndDate(plantId, date).orElse(null);

        // If no existing operation → CREATE NEW ONE
        if (exist == null) {
            // First fetch the parent plant
            FstpPlant plant = plantRepo.findPlantByPlantID(plantId).orElseThrow(() -> new IllegalStateException("Parent plant not found"));

            // Set parent reference and save new operation (for that date)
            plantOp.setPlant(plant);
            operationRepo.save(plantOp);
        }

        // existing operation by plant + date
        PlantOperation existing = operationRepo.getOperationByPlantIdAndDate(plantId, date).orElseThrow(() -> new IllegalStateException("Plant Operation Not Found For Date"));

        int count = 0;

        if (plantOp.getSludgeTankLevelAm() != null) count++;
        if (plantOp.getSludgeTankLevelPm() != null) count++;
        if (plantOp.getSludgeProcessed() != null) count++;
        if (plantOp.getBiocharProduced() != null) count++;
        if (plantOp.getPlantRunningHrs() != null) count++;
        if (plantOp.getPowerReadingAmImport() != null) count++;
        if (plantOp.getPowerReadingAmExport() != null) count++;
        if (plantOp.getPowerReadingPmImport() != null) count++;
        if (plantOp.getPowerReadingPmExport() != null) count++;
        if (plantOp.getDgReadingAm() != null) count++;
        if (plantOp.getDgReadingPm() != null) count++;
        if (plantOp.getDgDiesalPercentageAm() != null) count++;
        if (plantOp.getDgDiesalPercentagePm() != null) count++;
        if (plantOp.getPolymerUsage() != null) count++;
        if (plantOp.getPillets() != null) count++;
        if (plantOp.getPilletsStock() != null) count++;
        if (plantOp.getPolymerStock() != null) count++;
        if (plantOp.getPrivateVehicle() != null) count++;
        if (plantOp.getNoOfTripsPrivateVehicle() != null) count++;
        if (plantOp.getSludgeCollectPrivateVehicle() != null) count++;
        if (plantOp.getSludgeCollectPrivateVehicleKgs() != null) count++;
        if (plantOp.getPowerBill() != null) count++;
        if (plantOp.getLastBillDate() != null) count++;
        if (plantOp.getTotalNoOfUnits() != null) count++;
        if (plantOp.getTotalBillAmount() != null) count++;

        existing.setPrivateVehicle(plantOp.getPrivateVehicle());
        existing.setNoOfTripsPrivateVehicle(plantOp.getNoOfTripsPrivateVehicle());
        existing.setSludgeCollectPrivateVehicle(plantOp.getSludgeCollectPrivateVehicle());
        existing.setSludgeCollectPrivateVehicleKgs(plantOp.getSludgeCollectPrivateVehicleKgs());

        existing.setSludgeTankLevelAm(plantOp.getSludgeTankLevelAm());
        existing.setSludgeTankLevelPm(plantOp.getSludgeTankLevelPm());

        existing.setSludgeProcessed(plantOp.getSludgeProcessed());
        existing.setBiocharProduced(plantOp.getBiocharProduced());
        existing.setPlantRunningHrs(plantOp.getPlantRunningHrs());
        existing.setPowerReadingAmImport(plantOp.getPowerReadingAmImport());
        existing.setPowerReadingAmExport(plantOp.getPowerReadingAmExport());
        existing.setPowerReadingPmImport(plantOp.getPowerReadingPmImport());
        existing.setPowerReadingPmExport(plantOp.getPowerReadingPmExport());

        existing.setPowerBill(plantOp.getPowerBill());
        existing.setLastBillDate(plantOp.getLastBillDate());
        existing.setTotalNoOfUnits(plantOp.getTotalNoOfUnits());
        existing.setTotalBillAmount(plantOp.getTotalBillAmount());

        if (plantOp.getPowerReadingAmImport() != null && plantOp.getPowerReadingPmImport() != null) {
            existing.setPowerConsumed(Math.abs(plantOp.getPowerReadingPmImport() - plantOp.getPowerReadingAmImport()));
        } else {
            existing.setPowerConsumed(0.0);
        }
        existing.setDgReadingAm(plantOp.getDgReadingAm());
        existing.setDgReadingPm(plantOp.getDgReadingPm());
        existing.setDgDiesalPercentageAm(plantOp.getDgDiesalPercentageAm());
        existing.setDgDiesalPercentagePm(plantOp.getDgDiesalPercentagePm());
        if (plantOp.getDgReadingAm() != null && plantOp.getDgReadingPm() != null) {
            existing.setDgRunHours(Math.abs(plantOp.getDgReadingAm() - plantOp.getDgReadingPm()));
        } else {
            existing.setDgRunHours(0.0);
        }

        existing.setWaterUsed(plantOp.getWaterUsed());
        existing.setWaterFilledDate(plantOp.getWaterFilledDate());
        existing.setWaterLtrs(plantOp.getWaterLtrs());
        existing.setTotalWaterAmount(plantOp.getTotalWaterAmount());

        existing.setPolymerUsage(plantOp.getPolymerUsage());
        existing.setPolymerStock(plantOp.getPolymerStock());
        existing.setPillets(plantOp.getPillets());
        existing.setPilletsStock(plantOp.getPilletsStock());
        existing.setRemarks(plantOp.getRemarks());

        //save the update on db
        operationRepo.save(existing);

        //recompute totals
        recomputePlantTotals(plantId, date);

        Map<String, Integer> map = new HashMap<>();
        map.put("Operation Details Updated Successfully", count);
        return map;
    }

    //getting date wise (between) operation from db
    @Override
    public PlantOperation getAllOperationDataByIdAndDate(Long plantId, LocalDate date) {
        return operationRepo.getOperationByPlantIdAndDate(plantId, date).orElse(null);
    }

    @Override
    public List<PlantOperationDTO> getAllOperationBydate(LocalDate date) {
        List<PlantOperation> list = operationRepo.getAllOperationByDate(date);
        return list.stream().map(op -> new PlantOperationDTO(op.getPlant().getPlantID(), op)).toList();
    }

    @Override
    public List<PlantOperationBetweenDTO> findByOperationDateBetween(LocalDate startDate, LocalDate endDate) {

        List<PlantOperation> operations = operationRepo.findByOperationDateBetween(startDate, endDate);

        return operations.stream().map(op -> new PlantOperationBetweenDTO(op.getPlant() != null ? op.getPlant().getPlantID() : null, op)).toList();
    }


    //helper method
    @Transactional
    public void recomputePlantTotals(Long plantId, LocalDate date) {
        PlantOperation plantOp = operationRepo.getOperationByPlantIdAndDate(plantId, date).orElseThrow(() -> new IllegalStateException("Plant operation not found"));

        List<VehicleOperation> vehicles = vehicleOpRepo.findByPlantIdAndDate(plantId, date);

        //local variables for counting
        int totalTrips = 0;
        double totalSludge = 0.0;
        double totalSludgeKgs = 0.0;

        //multiple vehicles loop
        for (VehicleOperation vo : vehicles) {
            if (vo.getNoOfTrips() != null) totalTrips += vo.getNoOfTrips();

            if (vo.getSludgeCollect() != null) totalSludge += vo.getSludgeCollect();

            if (vo.getSludgeCollectKgs() != null) totalSludgeKgs += vo.getSludgeCollectKgs();
        }

        // private vehicle counted ONCE
        if (Boolean.TRUE.equals(plantOp.getPrivateVehicle())) {
            if (plantOp.getNoOfTripsPrivateVehicle() != null) totalTrips += plantOp.getNoOfTripsPrivateVehicle();

            if (plantOp.getSludgeCollectPrivateVehicle() != null)
                totalSludge += plantOp.getSludgeCollectPrivateVehicle();

            if (plantOp.getSludgeCollectPrivateVehicleKgs() != null)
                totalSludgeKgs += plantOp.getSludgeCollectPrivateVehicleKgs();
        }

        //set the values of sludge
        plantOp.setTotalNoOfTrips(totalTrips);
        plantOp.setSludgeReceived(totalSludge);
        plantOp.setSludgeReceivedKgs(totalSludgeKgs);

        //saving the plant operation in db
        operationRepo.save(plantOp);
    }

//    @Override
//    public PlantOperation getLatestPowerBill(Long plantId) {
//        return operationRepo.findLatestPowerBill(plantId).stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public PlantOperation getLatestWaterFilled(Long plantId) {
//        return operationRepo.findLatestWaterFilled(plantId).stream().findFirst().orElse(null);
//    }

    @Override
    public PowerBillDTO getLatestPowerBill(Long plantId, LocalDate date) {
        return operationRepo.findLatestPowerBillTillDate(plantId, date).stream().findFirst().orElse(null);
    }

    @Override
    public WaterDTO getLatestWaterFilled(Long plantId, LocalDate date) {
        return operationRepo.findLatestWaterFilledTillDate(plantId, date).stream().findFirst().orElse(null);
    }

//    @Override
//    public List<WaterDTO> getAllWaterDetails() {
//        return operationRepo.findAllWaterDetails();
//    }
//
//    @Override
//    public List<PowerBillDTO> getAllPowerBillDetails() {
//        return operationRepo.findAllPowerBillDetails();
//    }

    @Override
    public List<WaterDTO> getWaterDetailsByPlantId(Long plantId) {
        return operationRepo.findWaterDetailsByPlantId(plantId);
    }

    @Override
    public List<PowerBillDTO> getPowerBillDetailsByPlantId(Long plantId) {
        return operationRepo.findPowerBillDetailsByPlantId(plantId);
    }

}