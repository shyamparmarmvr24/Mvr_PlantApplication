package com.mvr.plant.controller;
import com.mvr.plant.DTO.SseActions;
import com.mvr.plant.DTO.SseEntities;
import com.mvr.plant.DTO.SseEvent;
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

    @Autowired
    private SseController sseController;

    // CREATE PRIVATE VEHICLE
    @PostMapping("/create/{plantId}")
    public ResponseEntity<PrivateVehicleDetails> createPrivateVehicle(@PathVariable Long plantId, @RequestBody PrivateVehicleDetails privateVehicle) {

        PrivateVehicleDetails saved = privateVehicleService.createPrivateVehicle(plantId, privateVehicle);
        sseController.sendEvent(
                new SseEvent(SseEntities.PRIVATE_VEHICLE, SseActions.CREATE, plantId, saved.getPrivateVehicleID())
        );
        return ResponseEntity.ok(saved);
    }

    // GET PRIVATE VEHICLES BY PLANT + DATE
    @GetMapping("/privateveh/{plantId}")
    public ResponseEntity<List<PrivateVehicleDetails>> getPrivateVehiclesByPlantAndDate(@PathVariable Long plantId, @RequestParam("date") LocalDate date) {
        List<PrivateVehicleDetails> list = privateVehicleService.getPrivateVehicleInformationByPlantIdAndDate(plantId, date);
        return ResponseEntity.ok(list);
    }

    // GET PRIVATE VEHICLES BY PLANT + DATE RANGE
    @GetMapping("/rangeBetween/{plantId}")
    public ResponseEntity<List<PrivateVehicleDetails>> getPrivateVehiclesByPlantAndDateRange(@PathVariable Long plantId, @RequestParam("fromDate") LocalDate fromDate, @RequestParam("toDate") LocalDate toDate)
    {
        List<PrivateVehicleDetails> list = privateVehicleService.findPrivateVehByPlantIdAndOperationDateBetween(plantId, fromDate, toDate);

        return ResponseEntity.ok(list);
    }

    // UPDATE PRIVATE VEHICLE
    @PutMapping("/update/{plantId}/{privateVehicleId}")
    public ResponseEntity<PrivateVehicleDetails> updatePrivateVehicle(@PathVariable Long plantId, @PathVariable Long privateVehicleId, @RequestBody PrivateVehicleDetails updated) {
        PrivateVehicleDetails result = privateVehicleService.updateVehicle(plantId, privateVehicleId, updated);
        sseController.sendEvent(
                new SseEvent(SseEntities.PRIVATE_VEHICLE, SseActions.UPDATE, plantId, privateVehicleId)
        );
        return ResponseEntity.ok(result);
    }

    // DELETE PRIVATE VEHICLE
    @DeleteMapping("/{privateVehicleId}")
    public void deletePrivateVehicle(@PathVariable Long privateVehicleId) {
        sseController.sendEvent(
                new SseEvent(SseEntities.PRIVATE_VEHICLE,SseActions.DELETE, null, privateVehicleId)
        );
        privateVehicleService.deletePrivateVehicle(privateVehicleId);
    }

}
