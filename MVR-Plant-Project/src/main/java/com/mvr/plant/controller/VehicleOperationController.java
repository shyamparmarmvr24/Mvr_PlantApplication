package com.mvr.plant.controller;

import com.mvr.plant.DTO.VehicleOperationBetweenDTO;
import com.mvr.plant.DTO.VehicleOperationDTO;
import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.entity.VehicleOperation;
import com.mvr.plant.repository.IVehicleOperationRepoMgmt;
import com.mvr.plant.repository.IVehicleRepo;
import com.mvr.plant.service.IVehicleOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicle-operations")
@CrossOrigin(origins = "*")
public class VehicleOperationController
{
    @Autowired
    private IVehicleOperationService vehicleOperationService;

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

        VehicleOperation op = vehicleOperationService.getVehicleOperationByVehicleIdAndDate(vehicleId, localDate);

        return ResponseEntity.ok(op);
    }


    // 3. PUT: Update OR Create Vehicle Operation
    @PutMapping("/{plantId}/{vehicleId}")
    public ResponseEntity<VehicleOperation> updateVehicleOperation(
            @PathVariable Long plantId,
            @PathVariable Long vehicleId,
            @RequestParam("date") String date,
            @RequestBody VehicleOperation vehicleOp
    ) {
        // set operation date before sending to repository logic
        vehicleOp.setOperationDate(LocalDate.parse(date));

        VehicleOperation response = vehicleOperationService.updateVehicleOperation(plantId, vehicleId, vehicleOp);

        // SSE broadcast event
        sseController.broadcastUpdate();

        return ResponseEntity.ok(response);
    }

    // inside VehicleOperationController
    @GetMapping("/date")
    public ResponseEntity<List<VehicleOperationDTO>> getVehicleOperationsByDate(
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);

        List<VehicleOperationDTO> list = vehicleOperationService.getVehicleOperationsByDate(localDate);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/vehicle/date-range")
    public ResponseEntity<List<VehicleOperationBetweenDTO>> getVehicleOperationsBetween(
            @RequestParam String start,
            @RequestParam String end)
    {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        List<VehicleOperationBetweenDTO> result =vehicleOperationService.getVehicleOperationsBetween(startDate, endDate);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{vehicleId}/latest-fuel")
    public ResponseEntity<VehicleOperation> latestFuel(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(vehicleOperationService.getLatestFuelFilled(vehicleId));
    }


}
