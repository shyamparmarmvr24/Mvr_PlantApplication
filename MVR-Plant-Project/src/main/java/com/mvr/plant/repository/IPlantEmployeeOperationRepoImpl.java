package com.mvr.plant.repository;

import com.mvr.plant.DTO.EmployeeOperationDTO;
import com.mvr.plant.entity.PlantEmployeeOperation;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPlantEmployeeOperationRepoImpl
{
    public Map<String,Integer> updatePlantEmployeeOperation(Long plantId,Integer empId, PlantEmployeeOperation empOp);
    public PlantEmployeeOperation getEmployeeOperationByEmpIdAndDate(Integer empId, LocalDate date);
    public List<PlantEmployeeOperation> getAllOperationsByEmployeeId(Integer employeeId);
    public List<EmployeeOperationDTO> getAllEmployeesOperationByDate(LocalDate date);
    public List<EmployeeOperationDTO> getAllEmployeesOperationBetween(LocalDate start, LocalDate end);
}
