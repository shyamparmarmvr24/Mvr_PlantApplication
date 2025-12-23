package com.mvr.plant.controller;

import com.mvr.plant.entity.PrivateVehicleDetails;
import com.mvr.plant.service.IPrivateVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/private-vehicles")
@CrossOrigin(origins = "*")
public class PrivateVehicleController {
    @Autowired
    private IPrivateVehicleService privateVehicleService;

    // CREATE PRIVATE VEHICLE
    @PostMapping("/create/{plantId}")
    public ResponseEntity<PrivateVehicleDetails> createPrivateVehicle(@PathVariable Long plantId, @RequestBody PrivateVehicleDetails privateVehicle) {

        PrivateVehicleDetails saved = privateVehicleService.createPrivateVehicle(plantId, privateVehicle);
        return ResponseEntity.ok(saved);
    }

    // GET PRIVATE VEHICLES BY PLANT + DATE
    @GetMapping("/privateveh/{plantId}")
    public ResponseEntity<List<PrivateVehicleDetails>> getPrivateVehiclesByPlantAndDate(@PathVariable Long plantId, @RequestParam("date") LocalDate date) {
        List<PrivateVehicleDetails> list = privateVehicleService.getPrivateVehicleInformationByPlantIdAndDate(plantId, date);
        return ResponseEntity.ok(list);
    }

    // UPDATE PRIVATE VEHICLE
    @PutMapping("/update/{plantId}/{privateVehicleId}")
    public ResponseEntity<PrivateVehicleDetails> updatePrivateVehicle(@PathVariable Long plantId, @PathVariable Long privateVehicleId, @RequestBody PrivateVehicleDetails updated) {
        PrivateVehicleDetails result = privateVehicleService.updateVehicle(plantId, privateVehicleId, updated);
        return ResponseEntity.ok(result);
    }

    // DELETE PRIVATE VEHICLE
    @DeleteMapping("/{privateVehicleId}")
    public void deletePrivateVehicle(@PathVariable Long privateVehicleId) {
        privateVehicleService.deletePrivateVehicle(privateVehicleId);
    }

}
