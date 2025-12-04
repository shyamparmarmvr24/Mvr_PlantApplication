package com.mvr.plant.controller;

import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.service.IPlantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plantemployees")
public class PlantEmployeeController
{
    @Autowired
    private IPlantEmployeeService employeeService;

    @Autowired
    private SseController sseController;

    //creating employee
    @PostMapping("/create/{plantId}")
    public ResponseEntity<PlantEmployee> createEmployee(@PathVariable Long plantId, @RequestBody PlantEmployee employee)
    {
        PlantEmployee savedEmployee = employeeService.createEmployee(plantId, employee);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(savedEmployee);
    }

    //getting employee data
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<PlantEmployee>> getEmployeesByPlant(@PathVariable Long plantId)
    {
        List<PlantEmployee> employees = employeeService.getAllEmployeeByPlantId(plantId);

        return ResponseEntity.ok(employees);
    }

    @PutMapping("/update/{plantId}/{employeeId}")
    public ResponseEntity<Map<String, String>> updateEmployee(
            @PathVariable Long plantId,
            @PathVariable Integer employeeId,
            @RequestBody PlantEmployee employee
    ) {
        String message = employeeService.updateOperationById(plantId, employeeId, employee);



        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        sseController.broadcastUpdate();
        return ResponseEntity.ok(response);
    }



}
