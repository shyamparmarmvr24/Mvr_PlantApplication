package com.mvr.plant.controller;

import com.mvr.plant.entity.MotorDetails;
import com.mvr.plant.service.IMotorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motor-details")
@CrossOrigin(origins = "*")
public class MotorDetailsController
{
    @Autowired
    private IMotorDetailsService motorService;


    // POST /api/motor-details/plant/{plantId}
    @PostMapping("/plant/{plantId}")
    public ResponseEntity<MotorDetails> createMotorDetails(@PathVariable Long plantId, @RequestBody MotorDetails motorDetails)
    {
        MotorDetails saved = motorService.createMotorDetails(plantId, motorDetails);
        return ResponseEntity.status(201).body(saved);
    }


    // PUT /api/motor-details/{motorId}
    @PutMapping("/{motorId}")
    public ResponseEntity<MotorDetails> updateMotorDetails(@PathVariable Long motorId, @RequestBody MotorDetails motorDetails)
    {
        MotorDetails updated = motorService.updateMotorDetails(motorId, motorDetails);
        return ResponseEntity.ok(updated);
    }


    // GET /api/motor-details/plant/{plantId}
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<MotorDetails>> getMotorDetailsByPlantId(@PathVariable Long plantId)
    {
        List<MotorDetails> list = motorService.getMotorDetailsByPlantId(plantId);
        return ResponseEntity.ok(list);
    }

    // DELETE /api/motor-details/{motorId}
    @DeleteMapping("/{motorId}")
    public ResponseEntity<String> deleteMotorDetails(@PathVariable Long motorId)
    {
        String result = motorService.deleteMotorDetails(motorId);

        if (result.toLowerCase().contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }

        return ResponseEntity.ok(result); // 200 OK + message
    }

}
