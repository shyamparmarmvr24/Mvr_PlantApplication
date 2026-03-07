package com.mvr.plant.controller;
import com.mvr.plant.DTO.EmployeeHistoryCheckDTO;
import com.mvr.plant.DTO.SseActions;
import com.mvr.plant.DTO.SseEntities;
import com.mvr.plant.DTO.SseEvent;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.repository.IPlantEmployeeHistoryImpl;
import com.mvr.plant.service.EmployeeHistoryService;
import com.mvr.plant.service.IPlantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plantemployees")
@CrossOrigin(origins = "*")
public class PlantEmployeeController {
    @Autowired
    private IPlantEmployeeService employeeService;

    @Autowired
    private SseController sseController;

    @Autowired
    private IPlantEmployeeHistoryImpl empHistory;

    @Autowired
    private EmployeeHistoryService historyService;

    @PostMapping("/create/{plantId}")
    public ResponseEntity<?> createEmployee(
            @PathVariable Long plantId,
            @RequestBody PlantEmployee employee,
            @RequestParam(required = false, defaultValue = "false") boolean forceCreate
    ) {

        // 🔥 CHECK HISTORY ONLY IF NOT FORCE
        if (!forceCreate) {
            EmployeeHistoryCheckDTO check =
                    historyService.checkHistory(employee.getEmployeeId());

            if (check.isExists()) {
                return ResponseEntity.ok(check);
            }
        }

        // NORMAL CREATE
        PlantEmployee savedEmployee =
                employeeService.createEmployee(plantId, employee);

        return ResponseEntity.ok(savedEmployee);
    }

    //getting employee data
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<PlantEmployee>> getEmployeesByPlant(@PathVariable Long plantId) {
        List<PlantEmployee> employees = employeeService.getAllEmployeeByPlantId(plantId);

        return ResponseEntity.ok(employees);
    }

    @PutMapping("/update/{plantId}/{employeeId}")
    public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable Long plantId, @PathVariable String employeeId, @RequestBody PlantEmployee employee
    ) {
        String message = employeeService.updateOperationById(plantId, employeeId, employee);


        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        sseController.sendEvent(
                new SseEvent(SseEntities.EMPLOYEE, SseActions.UPDATE, plantId, null)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plant/{plantId}/date/{date}")
    public ResponseEntity<List<PlantEmployee>> getEmployeesByPlantAndDate(@PathVariable Long plantId, @PathVariable String date)
    {

        LocalDate localDate = LocalDate.parse(date);

        List<PlantEmployee> employees = employeeService.getEmployeesByPlantIdAndDateWise(plantId, localDate);

        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {

        String result = empHistory.archiveAndDeleteEmployee(employeeId);

        if (result.contains("Successfully")) {
            sseController.sendEvent(
                    new SseEvent(SseEntities.EMPLOYEE, SseActions.DELETE, null, null)
            );
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(404).body(result);
    }
}
