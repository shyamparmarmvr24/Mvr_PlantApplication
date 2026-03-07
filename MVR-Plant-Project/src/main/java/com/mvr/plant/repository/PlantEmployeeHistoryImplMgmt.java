package com.mvr.plant.repository;
import com.mvr.plant.DTO.DeletedEmployeeOperationDTO;
import com.mvr.plant.DTO.EmployeeWithOperationsDTO;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlantEmployeeHistoryImplMgmt implements IPlantEmployeeHistoryImpl {

    @Autowired
    private IPlantEmployeeHistoryRepo empHistoryRepo;

    @Autowired
    private IPlantEmployeeOperationHistoryRepo empOpHistoryRepo;

    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Autowired
    private IPlantEmployeeOperationRepo empOpRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String archiveAndDeleteEmployee(String empId) {

        PlantEmployee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found"));

        // 1. MOVE EMPLOYEE → HISTORY
        entityManager.createNativeQuery("""
            INSERT INTO Plant_Employee_History_Table (
                employee_id, plant_id, employee_name, designation,
                mobile_no, alternate_mob_no, address,
                date_of_birth, date_of_joining, date_of_leaving,
                licence_type, licence_number, licence_issue_date, licence_expiry_date, archived_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """)
                .setParameter(1, employee.getEmployeeId())
                .setParameter(2, employee.getPlant().getPlantID())
                .setParameter(3, employee.getEmployeeName())
                .setParameter(4, employee.getDesignation())
                .setParameter(5, employee.getMobileNo())
                .setParameter(6, employee.getAlternateMobNo())
                .setParameter(7, employee.getAddress())
                .setParameter(8, employee.getDateOfBirth())
                .setParameter(9, employee.getDateOfJoining())
                .setParameter(10, employee.getDateOfLeaving())
                .setParameter(11, employee.getLicenceType())
                .setParameter(12, employee.getLicenceNumber())
                .setParameter(13, employee.getLicenceIssueDate())
                .setParameter(14, employee.getLicenceExpiryDate())
                .setParameter(15, LocalDateTime.now())
                .executeUpdate();

        // 2. MOVE OPERATIONS → HISTORY
        entityManager.createNativeQuery("""
            INSERT INTO Plant_Employee_Operation_History_Table (
                employee_id, date_of_joining, operation_date, attendance_am, attendance_pm, archived_at
            )
            SELECT employee_id, ?, operation_date, attendance_am, attendance_pm, ?
            FROM Plant_Employee_Operation_Table
            WHERE employee_id = ?
            AND operation_date >= ?
        """)
                .setParameter(1, employee.getDateOfJoining())
                .setParameter(2, LocalDateTime.now())
                .setParameter(3, empId)
                .setParameter(4, employee.getDateOfJoining())
                .executeUpdate();

        // 3. DELETE CHILD FIRST
        empOpRepo.deleteByEmployeeId(empId);

        // 4. DELETE EMPLOYEE
        empRepo.deleteById(empId);

        return "Employee Archived & Deleted Successfully";
    }

    @Override
    public List<PlantEmployeeHistory> getDeletedEmployeesByPlantIdAndDateWise(Long plantId, LocalDate date) {
        return empHistoryRepo.getEmployeesHistoryByPlantIdAndDate(plantId,date);
    }

    @Override
    public List<EmployeeWithOperationsDTO> getAllHistoryEmployeesOperations(LocalDate startDate, LocalDate endDate) {

        List<DeletedEmployeeOperationDTO> flatList =
                empOpHistoryRepo.getAllEmployeeOperationsBetweenDates(startDate, endDate);

        Map<String, EmployeeWithOperationsDTO> map = new LinkedHashMap<>();

        for (DeletedEmployeeOperationDTO row : flatList) {

            // unique key (employeeId + joiningDate)
            String key = row.getPlantId() + "_" + row.getEmployeeId() + "_" + row.getDateOfJoining();

            if (!map.containsKey(key)) {

                EmployeeWithOperationsDTO emp = new EmployeeWithOperationsDTO();
                emp.setPlantId(row.getPlantId());
                emp.setEmployeeId(row.getEmployeeId());
                emp.setEmployeeName(row.getEmployeeName());
                emp.setDesignation(row.getDesignation());
                emp.setMobileNo(row.getMobileNo());
                emp.setAddress(row.getAddress());
                emp.setDateOfBirth(row.getDateOfBirth());
                emp.setDateOfJoining(row.getDateOfJoining());
                emp.setDateOfLeaving(row.getDateOfLeaving());
                emp.setOperations(new ArrayList<>());

                map.put(key, emp);
            }

            // add operation
            EmployeeWithOperationsDTO.OperationDTO op =
                    new EmployeeWithOperationsDTO.OperationDTO(
                            row.getOperationId(),
                            row.getAttendanceAm(),
                            row.getAttendancePm(),
                            row.getOperationDate()
                    );

            map.get(key).getOperations().add(op);
        }

        return new ArrayList<>(map.values());
    }

}