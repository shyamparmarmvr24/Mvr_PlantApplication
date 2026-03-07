package com.mvr.plant.service;
import com.mvr.plant.DTO.EmployeeHistoryCheckDTO;
import com.mvr.plant.DTO.EmployeeHistoryItem;
import com.mvr.plant.entity.*;
import com.mvr.plant.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeHistoryService {

    @Autowired
    private IPlantEmployeeHistoryRepo historyRepo;

    @Autowired
    private IPlantEmployeeOperationHistoryRepo opHistoryRepo;

    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Autowired
    private IPlantEmployeeOperationRepo empOpRepo;

    @Autowired
    private IPlantRepository plantRepo;

    // CHECK HISTORY
    public EmployeeHistoryCheckDTO checkHistory(String empId) {

        List<PlantEmployeeHistory> list = historyRepo.findByEmployeeId(empId);

        if (list.isEmpty()) {
            return new EmployeeHistoryCheckDTO(false, null);
        }

        List<EmployeeHistoryItem> result = list.stream()
                .map(h -> new EmployeeHistoryItem(
                        h.getEmployeeId(),
                        h.getEmployeeName(),
                        h.getDesignation(),
                        h.getMobileNo(),
                        h.getAddress(),
                        h.getDateOfBirth(),
                        h.getDateOfJoining(),
                        h.getDateOfLeaving()
                )).collect(Collectors.toList());

        return new EmployeeHistoryCheckDTO(true, result);
    }

    // RESTORE EMPLOYEE
    @Transactional
    public String restoreEmployee(String empId, java.time.LocalDate joiningDate) {

        // OPTIONAL: clean existing (safe restore)
        if (empRepo.existsById(empId)) {
            empOpRepo.deleteByEmployeeId(empId);
            empRepo.deleteById(empId);
        }

        PlantEmployeeHistory history = historyRepo.findByEmployeeIdAndDateOfJoining(empId, joiningDate).orElseThrow(() -> new RuntimeException("History not found"));

        // CREATE MAIN EMPLOYEE
        PlantEmployee emp = new PlantEmployee();

        emp.setEmployeeId(history.getEmployeeId());
        emp.setEmployeeName(history.getEmployeeName());
        emp.setDesignation(history.getDesignation());
        emp.setMobileNo(history.getMobileNo());
        emp.setAlternateMobNo(history.getAlternateMobNo());
        emp.setAddress(history.getAddress());
        emp.setDateOfBirth(history.getDateOfBirth());
        emp.setDateOfJoining(history.getDateOfJoining());
        emp.setDateOfLeaving(null);

        emp.setLicenceType(history.getLicenceType());
        emp.setLicenceNumber(history.getLicenceNumber());
        emp.setLicenceIssueDate(history.getLicenceIssueDate());
        emp.setLicenceExpiryDate(history.getLicenceExpiryDate());

        emp.setPlant(plantRepo.findPlantByPlantID(history.getPlantId()).orElseThrow(() -> new RuntimeException("Plant not found")));

        // SAVE EMPLOYEE
        emp = empRepo.saveAndFlush(emp);

        // RESTORE OPERATIONS
        List<PlantEmployeeOperationHistory> ops = opHistoryRepo.findByEmployeeIdAndDateOfJoining(empId, joiningDate);

        for (PlantEmployeeOperationHistory opH : ops) {

            PlantEmployeeOperation op = new PlantEmployeeOperation();

            op.setEmployee(emp);
            op.setOperationDate(opH.getOperationDate());
            op.setAttendanceAm(opH.getAttendanceAm());
            op.setAttendancePm(opH.getAttendancePm());

            empOpRepo.save(op);
        }

        // 1. DELETE OPERATIONS FROM HISTORY
        opHistoryRepo.deleteByEmpIdAndJoiningDate(empId, joiningDate);

        // 2. DELETE EMPLOYEE FROM HISTORY
        historyRepo.deleteByEmpIdAndJoiningDate(empId, joiningDate);

        return "Employee Restored & Removed From History Successfully";
    }
}