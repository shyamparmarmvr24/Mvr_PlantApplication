package com.mvr.plant.controller;

import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.entity.VehicleOperation;
import com.mvr.plant.repository.IVehicleOperationRepoMgmt;
import com.mvr.plant.repository.IVehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicle-operations")
public class VehicleOperationController
{
    @Autowired
    private IVehicleOperationRepoMgmt vehicleOperationMgmt;

    @Autowired
    private SseController sseController;

    @Autowired
    private IVehicleRepo vehicleRepo;

    @GetMapping("/plant/{plantId}/vehicles")
    public ResponseEntity<List<VehicleInformation>> getVehiclesByPlant(@PathVariable Long plantId) {
        List<VehicleInformation> list = vehicleRepo.getVehicleInformationByPlantId(plantId);
        return ResponseEntity.ok(list);
    }

    //  GET: Fetch Vehicle Operation By VehicleId + Date
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleOperation> getVehicleOperation(
            @PathVariable Long vehicleId,
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);

        VehicleOperation op = vehicleOperationMgmt.getVehicleOperationByVehicleIdAndDate(vehicleId, localDate);

        return ResponseEntity.ok(op);
    }


    // 3. PUT: Update OR Create Vehicle Operation
    @PutMapping("/{plantId}/{vehicleId}")
    public ResponseEntity<Map<String, Integer>> updateVehicleOperation(
            @PathVariable Long plantId,
            @PathVariable Long vehicleId,
            @RequestParam("date") String date,
            @RequestBody VehicleOperation vehicleOp
    ) {
        // set operation date before sending to repository logic
        vehicleOp.setOperationDate(LocalDate.parse(date));

        Map<String, Integer> response = vehicleOperationMgmt.updateVehicleOperation(plantId, vehicleId, vehicleOp);

        // SSE broadcast event
        sseController.broadcastUpdate();

        return ResponseEntity.ok(response);
    }

}
