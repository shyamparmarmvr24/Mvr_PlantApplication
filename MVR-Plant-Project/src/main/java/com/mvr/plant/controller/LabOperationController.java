package com.mvr.plant.controller;

import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.service.ILaboratoryOperationService;
import com.mvr.plant.service.IPlantOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/laboperations")
public class LabOperationController
{
    @Autowired
    private ILaboratoryOperationService labService;

    @Autowired
    private SseController sseController;

    @PutMapping("/{plantId}")
    public ResponseEntity<Map<String,Integer>> updateOperation(
            @PathVariable Long plantId,
            @RequestParam("date") String date,
            @RequestBody LaboratoryOperation labOp)
    {

        // parse date and set into PlantOperation before service call
        LocalDate localDate = LocalDate.parse(date);
        labOp.setOperationDate(localDate);

        Map<String, Integer> result = labService.updateLabOperationById(plantId, labOp);

        sseController.broadcastUpdate();

        return ResponseEntity.ok(result);
    }


    @GetMapping("/plant/{plantId}")
    public ResponseEntity<LaboratoryOperation> getOperationByPlantAndDate(
            @PathVariable Long plantId,
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        LaboratoryOperation labOp = labService.getAllLabOperationDataByIdAndDate(plantId, localDate);
        return ResponseEntity.ok(labOp);
    }

}
