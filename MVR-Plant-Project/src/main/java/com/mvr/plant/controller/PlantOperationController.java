package com.mvr.plant.controller;

import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.service.IPlantOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/operations")
public class PlantOperationController
{
    @Autowired
    private IPlantOperationService plantOpService;

    @PutMapping("/{plantId}")
    public ResponseEntity<Map<String,Integer>> updateOperation(
            @PathVariable Long plantId,
            @RequestParam("date") String date,
            @RequestBody PlantOperation plantOp)
    {

        // parse date and set into PlantOperation before service call
        LocalDate localDate = LocalDate.parse(date);
        plantOp.setOperationDate(localDate);

        Map<String, Integer> result = plantOpService.updateOperationById(plantId, plantOp);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/plant/{plantId}")
    public ResponseEntity<PlantOperation> getOperationByPlantAndDate(
            @PathVariable Long plantId,
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        PlantOperation op = plantOpService.getAllOperationDataByIdAndDate(plantId, localDate);
        return ResponseEntity.ok(op);
    }

}
