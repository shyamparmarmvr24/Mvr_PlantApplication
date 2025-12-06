package com.mvr.plant.controller;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.service.IPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "*")
public class FstpPlantController
{
    @Autowired
    private IPlantService plantService;

    @Autowired
    private SseController sseController;

    // Create a new plant
    @PostMapping
    public ResponseEntity<FstpPlant> createPlant(@RequestBody FstpPlant plant)
    {
        FstpPlant result = plantService.insertPlantDetails(plant);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(result);
    }

    // Get all plants
    @GetMapping
    public ResponseEntity<List<FstpPlant>> getAllPlants() {
        List<FstpPlant> plants = plantService.getAllPlants();
        return ResponseEntity.ok(plants);
    }

    // Get plant by ID
    @GetMapping("/{id}")
    public ResponseEntity<FstpPlant> getPlantById(@PathVariable Long id) {
        FstpPlant plant = plantService.getPlantById(id);
        return ResponseEntity.ok(plant);
    }

    // Update plant by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlant(@PathVariable Long id, @RequestBody FstpPlant plant) {
        String result = plantService.updatePlantDetails(id, plant);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(result);
    }

    // Get plants by zone
    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<FstpPlant>> getPlantsByZone(@PathVariable Integer zone) {
        List<FstpPlant> plants = plantService.getPlantsByZone(zone);
        return ResponseEntity.ok(plants);
    }

}
