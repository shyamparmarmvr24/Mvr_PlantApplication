package com.mvr.plant.repository;

import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.entity.VehicleOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Repository
public class VehicleOperationRepoImpl implements IVehicleOperationRepoMgmt
{

    @Autowired
    private IVehicleOperationRepo vehicleOpRepo;

    @Autowired
    private IVehicleRepo vehicleRepo;

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

        if (vehicleOp.getVehicleReadingAm() != null) count++;
        if (vehicleOp.getVehicleReadingPm() != null) count++;
        if(vehicleOp.getVehicleFuelLevel() !=null) count++;

        existing.setVehicleReadingAm(vehicleOp.getVehicleReadingAm());
        existing.setVehicleReadingPm(vehicleOp.getVehicleReadingPm());
        existing.setVehicleFuelLevel(vehicleOp.getVehicleFuelLevel());

        vehicleOpRepo.save(existing);

        Map<String, Integer> map = new HashMap<>();
        map.put("Vehicle Operation Updated Successfully", count);
        return map;
    }
}
