package com.mvr.plant.repository;

import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPlantEmployeeRepoImpl
{
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee);
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId);
    public String updateOperationById(Long plantId,Integer empId, PlantEmployee employee);
    public String deleteEmployeeByEmpId(Integer empId);
}
