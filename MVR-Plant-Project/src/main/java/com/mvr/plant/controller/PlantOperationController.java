package com.mvr.plant.controller;

import com.mvr.plant.DTO.*;
import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.service.IPlantOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operations")
@CrossOrigin(origins = "*")
public class PlantOperationController {
    @Autowired
    private IPlantOperationService plantOpService;

    @Autowired
    private SseController sseController;   // ADD THIS

    @PutMapping("/{plantId}")
    public ResponseEntity<Map<String, Integer>> updateOperation(
            @PathVariable Long plantId,
            @RequestParam("date") String date,
            @RequestBody PlantOperation plantOp) {
        // parse date and set into PlantOperation before service call
        LocalDate localDate = LocalDate.parse(date);
        plantOp.setOperationDate(localDate);

        Map<String, Integer> result = plantOpService.updateOperationById(plantId, plantOp);

        sseController.sendEvent(
                new SseEvent("PLANT_OP", "UPDATE", plantId, null)
        );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/plant/{plantId}")
    public ResponseEntity<PlantOperation> getOperationByPlantAndDate(
            @PathVariable Long plantId,
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        PlantOperation op = plantOpService.getAllOperationDataByIdAndDate(plantId, localDate);
        if (op == null) {
            return ResponseEntity.ok().build(); // ✅ NO DATA ≠ ERROR
        }

        return ResponseEntity.ok(op);
    }

    @GetMapping("/date")
    public ResponseEntity<List<PlantOperationDTO>> getOperationsByDate(
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);

        List<PlantOperationDTO> operations = plantOpService.getAllOperationBydate(localDate);
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PlantOperationBetweenDTO>> getOperationsByDateRange(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<PlantOperationBetweenDTO> operations = plantOpService.findByOperationDateBetween(start, end);

        return ResponseEntity.ok(operations);
    }

//    @GetMapping("/plant/{plantId}/latest-power-bill")
//    public ResponseEntity<PlantOperation> latestPowerBill(@PathVariable Long plantId) {
//        return ResponseEntity.ok(plantOpService.getLatestPowerBill(plantId));
//    }
//
//    @GetMapping("/plant/{plantId}/latest-water")
//    public ResponseEntity<PlantOperation> latestWater(@PathVariable Long plantId) {
//        return ResponseEntity.ok(plantOpService.getLatestWaterFilled(plantId)
//        );
//    }

    @GetMapping("/plant/{plantId}/latest-power-bill")
    public ResponseEntity<PowerBillDTO> latestPowerBill(@PathVariable Long plantId, @RequestParam("date") LocalDate date)
    {
        return ResponseEntity.ok(plantOpService.getLatestPowerBill(plantId, date));
    }

    @GetMapping("/plant/{plantId}/latest-water")
    public ResponseEntity<WaterDTO> latestWater(@PathVariable Long plantId, @RequestParam("date") LocalDate date)
    {
        return ResponseEntity.ok(plantOpService.getLatestWaterFilled(plantId, date));
    }

//    @GetMapping("/water/all")
//    public ResponseEntity<List<WaterDTO>> getAllWaterDetails() {
//        return ResponseEntity.ok(plantOpService.getAllWaterDetails());
//    }
//
//    @GetMapping("/power-bill/all")
//    public ResponseEntity<List<PowerBillDTO>> getAllPowerBillDetails() {
//        return ResponseEntity.ok(plantOpService.getAllPowerBillDetails());
//    }

    @GetMapping("/water/{plantId}")
    public ResponseEntity<List<WaterDTO>> getWaterDetails(@PathVariable Long plantId) {
        return ResponseEntity.ok(plantOpService.getWaterDetailsByPlantId(plantId));
    }

    @GetMapping("/powerbill/{plantId}")
    public ResponseEntity<List<PowerBillDTO>> getPowerBillDetails(@PathVariable Long plantId) {
        return ResponseEntity.ok(plantOpService.getPowerBillDetailsByPlantId(plantId));
    }

}