package com.mvr.plant.repository;
import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantEmployee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlantEmployeeRepoImpl implements IPlantEmployeeRepoImpl
{
    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Override
    @Transactional
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee) {

        // Fetch Plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found: " + plantId));

        // Set FK plant reference
        employee.setPlant(plant);

        // Save employee
        return empRepo.save(employee);
    }

    @Override
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId)
    {
        return empRepo.getEmployeesByPlantId(plantId);
    }


    @Override
    public String updateOperationById(Long plantId, Integer empId, PlantEmployee employee)
    {
        // Find existing employee for this plant + employee ID
        PlantEmployee existing = empRepo
                .getEmployeeByPlantIdAndEmployeeId(plantId, empId)
                .orElseThrow(() -> new IllegalStateException("Employee Not Found For This Plant"));

        // ✅ Update allowed fields
        existing.setEmployeeName(employee.getEmployeeName());
        existing.setDesignation(employee.getDesignation());
        existing.setMobileNo(employee.getMobileNo());
        existing.setAlternateMobNo(employee.getAlternateMobNo());
        existing.setAddress(employee.getAddress());
        existing.setDateOfBirth(employee.getDateOfBirth());
        existing.setDateOfJoining(employee.getDateOfJoining());

        empRepo.save(existing);

        return "Employee Data Updated Successfully";
    }


}
