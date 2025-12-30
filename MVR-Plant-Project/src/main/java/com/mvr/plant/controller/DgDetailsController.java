package com.mvr.plant.controller;

import com.mvr.plant.DTO.SseEvent;
import com.mvr.plant.entity.DgDetails;
import com.mvr.plant.service.IDgDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dg-details")
@CrossOrigin(origins = "*")
public class DgDetailsController
{
    @Autowired
    private IDgDetailsService dgService;

    @Autowired
    private SseController sseController;

    // POST /api/dg-details/plant/{plantId}
    @PostMapping("/plant/{plantId}")
    public ResponseEntity<DgDetails> createDgDetails(@PathVariable Long plantId, @RequestBody DgDetails dgDetails)
    {
        DgDetails saved = dgService.createDgDetails(plantId, dgDetails);
        sseController.sendEvent(
                new SseEvent("DG", "CREATE", plantId, saved.getDgId())
        );
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /api/dg-details/{dgId}
    @PutMapping("/{dgId}")
    public ResponseEntity<DgDetails> updateDgDetails(@PathVariable Long dgId, @RequestBody DgDetails dgDetails)
    {
        DgDetails updated = dgService.updateDgDetails(dgId, dgDetails);
        sseController.sendEvent(
                new SseEvent("DG", "UPDATE", updated.getPlant().getPlantID(), dgId)
        );
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/plant/{plantId}")
    public ResponseEntity<DgDetails> getDgDetailsByPlantId(@PathVariable Long plantId) {

        return dgService.getDgDetailsByPlantId(plantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/dg-details/{dgId}
    @DeleteMapping("/{dgId}")
    public ResponseEntity<String> deleteDgDetails(@PathVariable Long dgId)
    {
        String msg = dgService.deleteDgDetails(dgId);
        return ResponseEntity.ok(msg); // 204 NO CONTENT
    }

}
