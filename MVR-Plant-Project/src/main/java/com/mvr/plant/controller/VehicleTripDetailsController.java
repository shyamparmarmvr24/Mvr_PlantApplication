package com.mvr.plant.controller;

import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleTripDetails;
import com.mvr.plant.service.IVehicleTripDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-trip-details")
public class VehicleTripDetailsController {
    @Autowired
    private IVehicleTripDetailsService vehicleTripService;

    @PutMapping("/operation/{vehicleOpId}")
    public ResponseEntity<VehicleTripDetails> createOrUpdateTrip(@PathVariable Long vehicleOpId, @RequestBody VehicleTripDetails tripDetails) {
        VehicleTripDetails saved = vehicleTripService.updateVehicle(vehicleOpId, tripDetails);
        return ResponseEntity.ok(saved);
    }

    // GET all trips for a given vehicle operation (date-wise)
    @GetMapping("/operation/{vehicleOpId}")
    public ResponseEntity<List<VehicleTripDetailsDTO>> getTripsByOperation(@PathVariable Long vehicleOpId) {
        return ResponseEntity.ok(vehicleTripService.getVehicleTripDetails(vehicleOpId));
    }
}