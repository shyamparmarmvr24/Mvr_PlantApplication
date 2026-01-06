package com.mvr.plant.controller;

import com.mvr.plant.DTO.EmployeeOperationDTO;
import com.mvr.plant.DTO.SseEvent;
import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.service.IPlantEmployeeOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee-operations")
@CrossOrigin(origins = "*")
public class PlantEmployeeOperationController {
    @Autowired
    private IPlantEmployeeOperationService employeeOpService;

    @Autowired
    private SseController sseController;

    @PostMapping("/update/{plantId}/{employeeId}")
    public ResponseEntity<Map<String, Integer>> updateOperation(@PathVariable Long plantId, @PathVariable Integer employeeId, @RequestBody PlantEmployeeOperation empOp) {
        Map<String, Integer> response = employeeOpService.updatePlantEmployeeOperation(plantId, employeeId, empOp);

        sseController.sendEvent(
                new SseEvent("EMP_OP", "UPDATE", plantId, employeeId.longValue())
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{employeeId}/date/{date}")
    public ResponseEntity<PlantEmployeeOperation> getByDate(@PathVariable Integer employeeId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);

        PlantEmployeeOperation op = employeeOpService
                .getEmployeeOperationByEmpIdAndDate(employeeId, localDate);

        if (op == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(op);
    }

    @GetMapping("/{employeeId}/all")
    public ResponseEntity<List<PlantEmployeeOperation>> getAllByEmployee(@PathVariable Integer employeeId) {
        List<PlantEmployeeOperation> list =
                employeeOpService.getAllOperationsByEmployeeId(employeeId);

        return ResponseEntity.ok(list);
    }

    // inside PlantEmployeeOperationController
    @GetMapping("/date")
    public ResponseEntity<List<EmployeeOperationDTO>> getEmployeeOperationsByDate(
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);

        List<EmployeeOperationDTO> list = employeeOpService.getAllEmployeesOperationByDate(localDate);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<EmployeeOperationDTO>> getEmployeeOperationsBetween(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        List<EmployeeOperationDTO> result = employeeOpService.getAllEmployeesOperationBetween(startDate, endDate);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/employee/{plantId}/{empId}")
    public ResponseEntity<PlantEmployeeOperation> updateEmployeeOperation(@PathVariable Long plantId, @PathVariable Integer empId,@RequestParam("date") String date, @RequestBody PlantEmployeeOperation empOp)
    {
        // parse and set date
        LocalDate localDate = LocalDate.parse(date);
        empOp.setOperationDate(localDate);

        PlantEmployeeOperation updated = employeeOpService.updatePlantEmployeeOperationByDate(plantId, empId, empOp);

        sseController.sendEvent(
                new SseEvent("EMP_OP", "UPDATE", plantId, empId.longValue())
        );

        return ResponseEntity.ok(updated);
    }

}
