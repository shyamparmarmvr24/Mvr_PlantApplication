package com.mvr.plant.service;

import com.mvr.plant.entity.PlantEmployee;

import java.util.List;

public interface IPlantEmployeeService
{
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee);
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId);
    public String updateOperationById(Long plantId,Integer empId, PlantEmployee employee);
    public String deleteEmployeeByEmpId(Integer empId);
}
