package com.mvr.plant.controller;
import com.mvr.plant.DTO.SseActions;
import com.mvr.plant.DTO.SseEntities;
import com.mvr.plant.DTO.SseEvent;
import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleTripDetails;
import com.mvr.plant.service.IVehicleTripDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle-trip-details")
@CrossOrigin(origins = "*")
public class VehicleTripDetailsController {
    @Autowired
    private IVehicleTripDetailsService vehicleTripService;

    @Autowired
    private SseController sseController;

    @PutMapping("/operation/{vehicleOpId}")
    public ResponseEntity<VehicleTripDetails> createOrUpdateTrip(@PathVariable Long vehicleOpId, @RequestBody VehicleTripDetails tripDetails) {
        VehicleTripDetails saved = vehicleTripService.updateVehicle(vehicleOpId, tripDetails);
        sseController.sendEvent(
                new SseEvent(SseEntities.VEHICLE_TRIP, SseActions.UPDATE, null, vehicleOpId)
        );
        return ResponseEntity.ok(saved);
    }

    // GET all trips for a given vehicle operation (date-wise)
    @GetMapping("/operation/{vehicleOpId}")
    public ResponseEntity<List<VehicleTripDetailsDTO>> getTripsByOperation(@PathVariable Long vehicleOpId) {
        return ResponseEntity.ok(vehicleTripService.getVehicleTripDetails(vehicleOpId));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long tripId) {

        String result = vehicleTripService.deleteTrip(tripId);

        if ("Trip Deleted Successfully".equals(result)) {
            sseController.sendEvent(
                    new SseEvent(SseEntities.VEHICLE_TRIP, SseActions.DELETE, null, tripId)
            );
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}