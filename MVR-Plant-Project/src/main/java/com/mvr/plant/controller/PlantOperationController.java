package com.mvr.plant.controller;
import com.mvr.plant.DTO.PlantOperationBetweenDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
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
public class PlantOperationController
{
    @Autowired
    private IPlantOperationService plantOpService;

    @Autowired
    private SseController sseController;   // ADD THIS

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

        sseController.broadcastUpdate();

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

    @GetMapping("/plant/{plantId}/latest-power-bill")
    public ResponseEntity<PlantOperation> latestPowerBill(@PathVariable Long plantId)
    {
        return ResponseEntity.ok(plantOpService.getLatestPowerBill(plantId));
    }
}