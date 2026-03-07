package com.mvr.plant.service;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.repository.IPlantEmployeeRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PlantEmployeeServiceImpl implements IPlantEmployeeService {
    @Autowired
    private IPlantEmployeeRepoImpl empRepo;

    @Override
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee) {
        return empRepo.createEmployee(plantId, employee);
    }

    @Override
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId) {
        return empRepo.getAllEmployeeByPlantId(plantId);
    }

    @Override
    public String updateOperationById(Long plantId, String empId, PlantEmployee employee) {
        return empRepo.updateOperationById(plantId, empId, employee);
    }

    @Override
    public String deleteEmployeeByEmpId(String empId) {
        return empRepo.deleteEmployeeByEmpId(empId);
    }

    @Override
    public List<PlantEmployee> getEmployeesByPlantIdAndDateWise(Long plantId, LocalDate date) {
        return empRepo.getEmployeesByPlantIdAndDateWise(plantId,date);
    }
}
