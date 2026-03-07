package com.mvr.plant.controller;
import com.mvr.plant.DTO.EmployeeHistoryCheckDTO;
import com.mvr.plant.DTO.EmployeeWithOperationsDTO;
import com.mvr.plant.entity.PlantEmployeeHistory;
import com.mvr.plant.repository.IPlantEmployeeHistoryImpl;
import com.mvr.plant.repository.PlantEmployeeHistoryImplMgmt;
import com.mvr.plant.service.EmployeeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/historyEmployees")
@CrossOrigin(origins = "*")
public class EmployeeHistoryController
{
    @Autowired
    private IPlantEmployeeHistoryImpl empHistory;

    @Autowired
    private EmployeeHistoryService historyService;

    @Autowired
    private PlantEmployeeHistoryImplMgmt employeeHistory;

    @GetMapping("/check-history/{employeeId}")
    public ResponseEntity<EmployeeHistoryCheckDTO> checkHistory(@PathVariable String employeeId) {
        return ResponseEntity.ok(historyService.checkHistory(employeeId));
    }

    @PostMapping("/restore/{employeeId}")
    public ResponseEntity<String> restoreEmployee(@PathVariable String employeeId, @RequestParam String joiningDate)
    {
        String result = historyService.restoreEmployee(employeeId, LocalDate.parse(joiningDate));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/employees/{plantId}/date/{date}")
    public ResponseEntity<List<PlantEmployeeHistory>> getDeletedEmployeesByPlantAndDate(@PathVariable Long plantId, @PathVariable String date)
    {
        LocalDate localDate = LocalDate.parse(date);

        List<PlantEmployeeHistory> employees = employeeHistory.getDeletedEmployeesByPlantIdAndDateWise(plantId, localDate);

        return ResponseEntity.ok(employees);
    }


    @GetMapping("/employee-operations")
    public ResponseEntity<List<EmployeeWithOperationsDTO>> getOperations(@RequestParam String startDate, @RequestParam String endDate)
    {
        List<EmployeeWithOperationsDTO> data = employeeHistory.getAllHistoryEmployeesOperations(LocalDate.parse(startDate), LocalDate.parse(endDate));

        return ResponseEntity.ok(data);
    }
}
