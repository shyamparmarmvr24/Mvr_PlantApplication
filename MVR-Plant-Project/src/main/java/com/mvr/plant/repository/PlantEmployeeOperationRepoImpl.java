package com.mvr.plant.repository;

import com.mvr.plant.DTO.EmployeeOperationDTO;
import com.mvr.plant.DTO.PlantOperationDTO;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlantEmployeeOperationRepoImpl implements IPlantEmployeeOperationRepoImpl {
    @Autowired
    private IPlantEmployeeOperationRepo empOpRepo;

    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Override
    public Map<String, Integer> updatePlantEmployeeOperation(Long plantId, Integer empId, PlantEmployeeOperation empOp) {
        LocalDate date = empOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // Find existing operation for plant + date
        PlantEmployeeOperation exist = empOpRepo
                .getEmployeeOperationByEmployeeIdAndDate(empId, date)
                .orElse(null);

        // If no existing operation → CREATE NEW ONE
        if (exist == null) {
            // First fetch the parent plant
            PlantEmployee plantEmp = empRepo.getEmployeeByPlantIdAndEmployeeId(plantId, empId)
                    .orElseThrow(() -> new IllegalStateException("Employee Not Found"));

            // Set parent reference and save new operation (for that date)
            empOp.setEmployee(plantEmp);
            empOpRepo.save(empOp);
        }

        // existing operation by plant + date
        PlantEmployeeOperation existing = empOpRepo
                .getEmployeeOperationByEmployeeIdAndDate(empId, date)
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
    public PlantEmployeeOperation getEmployeeOperationByEmpIdAndDate(Integer empId, LocalDate date) {
        return empOpRepo.getEmployeeOperationByEmployeeIdAndDate(empId, date).orElse(null);
    }

    @Override
    public List<PlantEmployeeOperation> getAllOperationsByEmployeeId(Integer employeeId) {
        return empOpRepo.getAllOperationsByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeOperationDTO> getAllEmployeesOperationByDate(LocalDate date) {
        // 1) fetch operations for the date with employee + plant eagerly loaded
        List<PlantEmployeeOperation> ops = empOpRepo.getAllOperationByDate(date);

        // 2) map each operation -> EmployeeOperationDTO (explicit mapping)
        List<EmployeeOperationDTO> result = new ArrayList<>();
        for (PlantEmployeeOperation op : ops) {
            if (op == null) continue;

            EmployeeOperationDTO dto = new EmployeeOperationDTO();

            if (op.getEmployee() != null) {
                dto.setEmployeeId(op.getEmployee().getEmployeeId());
                dto.setEmployeeName(op.getEmployee().getEmployeeName());
                dto.setDesination(op.getEmployee().getDesignation());
                if (op.getEmployee().getPlant() != null) {
                    dto.setPlantId(op.getEmployee().getPlant().getPlantID());
                } else {
                    dto.setPlantId(null);
                }
            } else {
                dto.setEmployeeId(null);
                dto.setPlantId(null);
            }

            dto.setPlantOp(op);

            result.add(dto);
        }

        return result;
    }

    @Override
    public List<EmployeeOperationDTO> getAllEmployeesOperationBetween(LocalDate start, LocalDate end) {
        List<PlantEmployeeOperation> ops = empOpRepo.findByOperationDateBetween(start, end);

        List<EmployeeOperationDTO> result = new ArrayList<>();

        for (PlantEmployeeOperation op : ops) {

            if (op == null) continue;

            EmployeeOperationDTO dto = new EmployeeOperationDTO();

            if (op.getEmployee() != null) {

                dto.setEmployeeId(op.getEmployee().getEmployeeId());
                dto.setEmployeeName(op.getEmployee().getEmployeeName());
                dto.setDesination(op.getEmployee().getDesignation());

                if (op.getEmployee().getPlant() != null) {
                    dto.setPlantId(op.getEmployee().getPlant().getPlantID());
                }
            }

            dto.setPlantOp(op);

            result.add(dto);
        }

        return result;
    }

    @Override
    @Transactional
    public PlantEmployeeOperation updatePlantEmployeeOperationByDate(Long plantId, Integer empId, PlantEmployeeOperation empOp)
    {
        LocalDate date = empOp.getOperationDate();
        if (date == null) {
            throw new IllegalStateException("operationDate is required");
        }

        // 1️⃣ Fetch employee under plant
        PlantEmployee plantEmp = empRepo
                .getEmployeeByPlantIdAndEmployeeId(plantId, empId)
                .orElseThrow(() -> new IllegalStateException("Employee Not Found"));

        // 2️⃣ Fetch existing operation (if any)
        PlantEmployeeOperation existing = empOpRepo
                .getEmployeeOperationByEmployeeIdAndDate(empId, date)
                .orElse(null);

        // 3️⃣ Create new if not exists
        if (existing == null) {
            existing = new PlantEmployeeOperation();
            existing.setEmployee(plantEmp);
            existing.setOperationDate(date);
        }

        // 4️⃣ Update fields
        existing.setAttendanceAm(empOp.getAttendanceAm());
        existing.setAttendancePm(empOp.getAttendancePm());

        // 5️⃣ Save & return entity
        return empOpRepo.save(existing);
    }

}
