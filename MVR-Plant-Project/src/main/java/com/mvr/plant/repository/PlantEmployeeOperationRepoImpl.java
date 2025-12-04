package com.mvr.plant.repository;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlantEmployeeOperationRepoImpl implements IPlantEmployeeOperationRepoImpl
{
    @Autowired
    private IPlantEmployeeOperationRepo empOpRepo;

    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Override
    public Map<String, Integer> updatePlantEmployeeOperation(Long plantId,Integer empId, PlantEmployeeOperation empOp)
    {
        LocalDate date = empOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // Find existing operation for plant + date
        PlantEmployeeOperation exist = empOpRepo
                .getEmployeeOperationByEmployeeIdAndDate(empId,date)
                .orElse(null);

        // If no existing operation → CREATE NEW ONE
        if (exist == null) {
            // First fetch the parent plant
            PlantEmployee plantEmp = empRepo.getEmployeeByPlantIdAndEmployeeId(plantId,empId)
                    .orElseThrow(() -> new IllegalStateException("Employee Not Found"));

            // Set parent reference and save new operation (for that date)
            empOp.setEmployee(plantEmp);
            empOpRepo.save(empOp);
        }

        // existing operation by plant + date
        PlantEmployeeOperation existing = empOpRepo
                .getEmployeeOperationByEmployeeIdAndDate(empId,date)
                .orElseThrow(() -> new IllegalStateException("Employee Operation Not Found For Date"));

        int count = 0;

        if (empOp.getAttendanceAm() != null) count++;
        if (empOp.getAttendancePm() != null) count++;

        existing.setAttendanceAm(empOp.getAttendanceAm());
        existing.setAttendancePm(empOp.getAttendancePm());

        empOpRepo.save(existing);

        Map<String, Integer> map = new HashMap<>();
        map.put("Employee Operation Updated Successfully", count);
        return map;
    }

    @Override
    public PlantEmployeeOperation getEmployeeOperationByEmpIdAndDate(Integer empId, LocalDate date)
    {
        return empOpRepo.getEmployeeOperationByEmployeeIdAndDate(empId,date).orElseThrow(()->new IllegalStateException("Employee Operation Not Found For Date"));
    }

    @Override
    public List<PlantEmployeeOperation> getAllOperationsByEmployeeId(Integer employeeId)
    {
        return empOpRepo.getAllOperationsByEmployeeId(employeeId);
    }
}
