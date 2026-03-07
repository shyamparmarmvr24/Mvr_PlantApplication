package com.mvr.plant.repository;
import com.mvr.plant.entity.PlantEmployee;
import java.time.LocalDate;
import java.util.List;

public interface IPlantEmployeeRepoImpl
{
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee);
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId);
    public String updateOperationById(Long plantId,String empId, PlantEmployee employee);
    public String deleteEmployeeByEmpId(String empId);
    public List<PlantEmployee> getEmployeesByPlantIdAndDateWise(Long plantId, LocalDate date);
}
