package com.mvr.plant.repository;
import com.mvr.plant.DTO.EmployeeOperationDTO;
import com.mvr.plant.entity.PlantEmployeeOperation;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPlantEmployeeOperationRepoImpl
{
    public Map<String,Integer> updatePlantEmployeeOperation(Long plantId,String empId, PlantEmployeeOperation empOp);
    public PlantEmployeeOperation getEmployeeOperationByEmpIdAndDate(String empId, LocalDate date);
    public List<PlantEmployeeOperation> getAllOperationsByEmployeeId(String employeeId);
    public List<EmployeeOperationDTO> getAllEmployeesOperationByDate(LocalDate date);
    public List<EmployeeOperationDTO> getAllEmployeesOperationBetween(LocalDate start, LocalDate end);
    public PlantEmployeeOperation updatePlantEmployeeOperationByDate(Long plantId, String empId,PlantEmployeeOperation empOp);
}
