package com.mvr.plant.service;

import com.mvr.plant.DTO.EmployeeOperationDTO;
import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.repository.IPlantEmployeeOperationRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PlantEmployeeOperationServiceImpl implements IPlantEmployeeOperationService
{
    @Autowired
    private IPlantEmployeeOperationRepoImpl empOpRepo;

    @Override
    public Map<String, Integer> updatePlantEmployeeOperation(Long plantId, Integer empId, PlantEmployeeOperation empOp)
    {
        return empOpRepo.updatePlantEmployeeOperation(plantId,empId,empOp);
    }

    @Override
    public PlantEmployeeOperation getEmployeeOperationByEmpIdAndDate(Integer empId, LocalDate date) {
        return empOpRepo.getEmployeeOperationByEmpIdAndDate(empId,date);
    }

    @Override
    public List<PlantEmployeeOperation> getAllOperationsByEmployeeId(Integer employeeId) {
        return empOpRepo.getAllOperationsByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeOperationDTO> getAllEmployeesOperationByDate(LocalDate date)
    {
        return empOpRepo.getAllEmployeesOperationByDate(date);
    }

    @Override
    public List<EmployeeOperationDTO> getAllEmployeesOperationBetween(LocalDate start, LocalDate end) {
        return empOpRepo.getAllEmployeesOperationBetween(start,end);
    }

}
