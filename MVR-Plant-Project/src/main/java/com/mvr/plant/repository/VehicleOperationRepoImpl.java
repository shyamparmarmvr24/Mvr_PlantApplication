package com.mvr.plant.repository;
import com.mvr.plant.DTO.VehicleOperationBetweenDTO;
import com.mvr.plant.DTO.VehicleOperationDTO;
import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.entity.VehicleOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class VehicleOperationRepoImpl implements IVehicleOperationRepoMgmt
{

    @Autowired
    private IVehicleOperationRepo vehicleOpRepo;

    @Autowired
    private IVehicleRepo vehicleRepo;

    @Autowired
    private IPlantOperationMgmtRepo plantOp;

    @Override
    public VehicleOperation getVehicleOperationByVehicleIdAndDate(Long vehicleID, LocalDate date)
    {
        return vehicleOpRepo.getVehicleOperationByVehicleIdAndDate(vehicleID,date).orElseThrow(()->new IllegalStateException("Vehicle Operation Not Found"));
    }

    @Override
    public Map<String, Integer> updateVehicleOperation(Long plantId, Long vehicleID, VehicleOperation vehicleOp)
    {

        LocalDate date = vehicleOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // Find existing operation for plant + date
        VehicleOperation exist = vehicleOpRepo
                .getVehicleOperationByVehicleIdAndDate(vehicleID,date)
                .orElse(null);

        // If no existing operation → CREATE NEW ONE
        if (exist == null) {
            // First fetch the parent plant
            VehicleInformation vehicleInfo = vehicleRepo.findByPlantIdAndVehicleId(plantId,vehicleID)
                    .orElseThrow(() -> new IllegalStateException("Vehicle Not Found"));

            // Set parent reference and save new operation (for that date)
            vehicleOp.setVehicle(vehicleInfo);
            vehicleOpRepo.save(vehicleOp);
        }

        VehicleOperation existing = vehicleOpRepo
                .getVehicleOperationByVehicleIdAndDate(vehicleID,date)
                .orElseThrow(()->new IllegalStateException("Vehicle Operation Not Found For Date"));

        int count = 0;

        if(vehicleOp.getVehicleReadingAm() != null) count++;
        if(vehicleOp.getVehicleReadingPm() != null) count++;
        if(vehicleOp.getVehicleFuelLevel() !=null) count++;
        if(vehicleOp.getLastFuelFilled() !=null) count++;
        if(vehicleOp.getLastFuelFilledDate() !=null) count++;
        if(vehicleOp.getFilledLiters() != null) count++;
        if(vehicleOp.getNoOfTrips() != null) count++;
        if(vehicleOp.getSludgeCollect() != null) count++;
        if(vehicleOp.getSludgeCollectKgs() != null) count++;


        existing.setVehicleReadingAm(vehicleOp.getVehicleReadingAm());
        existing.setVehicleReadingPm(vehicleOp.getVehicleReadingPm());
        existing.setVehicleFuelLevel(vehicleOp.getVehicleFuelLevel());
        existing.setLastFuelFilled(vehicleOp.getLastFuelFilled());
        existing.setLastFuelFilledDate(vehicleOp.getLastFuelFilledDate());
        existing.setFilledLiters(vehicleOp.getFilledLiters());
        existing.setNoOfTrips(vehicleOp.getNoOfTrips());

        existing.setSludgeCollect(vehicleOp.getSludgeCollect());
        existing.setSludgeCollectKgs(vehicleOp.getSludgeCollectKgs());

        vehicleOpRepo.save(existing);


        //this save
        plantOp.recomputePlantTotals(plantId, date);

        Map<String, Integer> map = new HashMap<>();
        map.put("Vehicle Operation Updated Successfully", count);
        return map;
    }

    @Override
    public List<VehicleOperationDTO> getVehicleOperationsByDate(LocalDate date)
    {
        // 1) fetch operations with vehicle and plant eagerly loaded
        List<VehicleOperation> ops = vehicleOpRepo.findByOperationDateWithVehicleAndPlant(date);

        // 2) explicit mapping to DTO
        List<VehicleOperationDTO> result = new ArrayList<>();
        for (VehicleOperation op : ops) {
            if (op == null) continue;

            VehicleOperationDTO dto = new VehicleOperationDTO();

            if (op.getVehicle() != null) {
                dto.setVehicle(op.getVehicle());

                if (op.getVehicle().getPlant() != null) {
                    dto.setPlantId(op.getVehicle().getPlant().getPlantID());
                } else {
                    dto.setPlantId(null);
                }
            } else {
                dto.setVehicle(null);
                dto.setPlantId(null);
            }

            dto.setVehicleOp(op);

            result.add(dto);
        }
        return result;
    }

    @Override
    public List<VehicleOperationBetweenDTO> getVehicleOperationsBetween(LocalDate start, LocalDate end)
    {
        List<VehicleOperation> ops = vehicleOpRepo.findByOperationDateBetween(start, end);

        List<VehicleOperationBetweenDTO> result = new ArrayList<>();

        for (VehicleOperation op : ops) {

            if (op == null) continue;

            VehicleInformation vehicle = op.getVehicle();
            Long plantId = null;

            if (vehicle != null && vehicle.getPlant() != null) {
                plantId = vehicle.getPlant().getPlantID();
            }

            result.add(
                    new VehicleOperationBetweenDTO(
                            plantId,
                            vehicle,
                            op
                    )
            );
        }

        return result;
    }

    @Override
    public VehicleOperation getLatestFuelFilled(Long vehicleId) {
        return vehicleOpRepo.findLatestFuelFilled(vehicleId).stream().findFirst().orElse(null);
    }

}

