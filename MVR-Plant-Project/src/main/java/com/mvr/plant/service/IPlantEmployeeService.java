package com.mvr.plant.service;
import com.mvr.plant.entity.PlantEmployee;
import java.time.LocalDate;
import java.util.List;

public interface IPlantEmployeeService
{
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee);
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId);
    public String updateOperationById(Long plantId,String empId, PlantEmployee employee);
    public String deleteEmployeeByEmpId(String empId);
    public List<PlantEmployee> getEmployeesByPlantIdAndDateWise(Long plantId, LocalDate date);
}
