package com.mvr.plant.repository;
import com.mvr.plant.DTO.EmployeeWithOperationsDTO;
import com.mvr.plant.entity.PlantEmployeeHistory;
import java.time.LocalDate;
import java.util.List;

public interface IPlantEmployeeHistoryImpl
{
    String archiveAndDeleteEmployee(String empId);
    public List<PlantEmployeeHistory> getDeletedEmployeesByPlantIdAndDateWise(Long plantId, LocalDate date);
    public List<EmployeeWithOperationsDTO> getAllHistoryEmployeesOperations(LocalDate startDate, LocalDate endDate);
}
