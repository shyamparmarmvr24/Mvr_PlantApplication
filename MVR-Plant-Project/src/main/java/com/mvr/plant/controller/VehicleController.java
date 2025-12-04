package com.mvr.plant.controller;

import com.mvr.plant.entity.VehicleInformation;
import com.mvr.plant.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiclesinformation")
public class VehicleController
{
    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private SseController sseController;


    //CREATE VEHICLE FOR PLANT

    @PostMapping("/create/{plantId}")
    public ResponseEntity<VehicleInformation> createVehicle(
            @PathVariable Long plantId,
            @RequestBody VehicleInformation vehicle
    ) {
        VehicleInformation saved = vehicleService.createVehicle(plantId, vehicle);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(saved);
    }


     //GET ALL VEHICLES FOR A PLANT

    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<VehicleInformation>> getVehicleByPlant(
            @PathVariable Long plantId
    ) {
        List<VehicleInformation> vehicles = vehicleService.getVehicleInformationByPlantId(plantId);
        return ResponseEntity.ok(vehicles);
    }


      //UPDATE VEHICLE (BY PLANT + VEHICLE ID)

    @PutMapping("/update/{plantId}/{vehicleId}")
    public ResponseEntity<VehicleInformation> updateVehicle(
            @PathVariable Long plantId,
            @PathVariable Long vehicleId,
            @RequestBody VehicleInformation updatedVehicle
    ) {
        VehicleInformation updated = vehicleService.updateVehicle(plantId, vehicleId, updatedVehicle);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(updated);
    }
}
