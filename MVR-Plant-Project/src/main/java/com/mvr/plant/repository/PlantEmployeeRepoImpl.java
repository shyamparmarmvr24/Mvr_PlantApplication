package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantEmployee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PlantEmployeeRepoImpl implements IPlantEmployeeRepoImpl {
    @Autowired
    private IPlantEmployeeRepo empRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Autowired
    private IPlantEmployeeOperationRepo empOpRepo;

    @Override
    @Transactional
    public PlantEmployee createEmployee(Long plantId, PlantEmployee employee) {

        // Fetch Plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId).orElseThrow(() -> new RuntimeException("Plant not found: " + plantId));

        // Set FK plant reference
        employee.setPlant(plant);

        // HANDLE DRIVER LICENCE
        handleDriverLicence(employee);

        // Save employee
        return empRepo.save(employee);
    }

    @Override
    public List<PlantEmployee> getAllEmployeeByPlantId(Long plantId) {
        return empRepo.getEmployeesByPlantId(plantId);
    }


    @Override
    public String updateOperationById(Long plantId, Integer empId, PlantEmployee employee) {
        // Find existing employee for this plant + employee ID
        PlantEmployee existing = empRepo.getEmployeeByPlantIdAndEmployeeId(plantId, empId).orElseThrow(() -> new IllegalStateException("Employee Not Found For This Plant"));

        // ✅ Update allowed fields
        existing.setEmployeeName(employee.getEmployeeName());
        existing.setDesignation(employee.getDesignation());
        existing.setMobileNo(employee.getMobileNo());
        existing.setAlternateMobNo(employee.getAlternateMobNo());
        existing.setAddress(employee.getAddress());
        existing.setDateOfBirth(employee.getDateOfBirth());
        existing.setDateOfJoining(employee.getDateOfJoining());

        //DRIVER LICENCE UPDATE (ONLY FOR DRIVER)
        if ("Driver".equalsIgnoreCase(employee.getDesignation())) {
            existing.setLicenceType(employee.getLicenceType());
            existing.setLicenceNumber(employee.getLicenceNumber());
            existing.setLicenceIssueDate(employee.getLicenceIssueDate());
            existing.setLicenceExpiryDate(employee.getLicenceExpiryDate());
        } else {
            existing.setLicenceType(null);
            existing.setLicenceNumber(null);
            existing.setLicenceIssueDate(null);
            existing.setLicenceExpiryDate(null);
        }

        empRepo.save(existing);

        return "Employee Data Updated Successfully";
    }

    @Override
    @Transactional   // ensure both deletes are part of one transaction
    public String deleteEmployeeByEmpId(Integer empId) {
        Optional<PlantEmployee> emp = empRepo.findById(empId);
        if (emp.isPresent()) {
            try {
                // delete child operations first (fast set-based delete)
                empOpRepo.deleteByEmployeeId(empId);

                // now delete employee
                empRepo.deleteById(empId);
                return "Employee Deleted Successfully";
            } catch (DataIntegrityViolationException ex) {
                // in case some other FK prevents delete
                return "Cannot delete employee " + empId + " due to related data. Remove dependent records first.";
            } catch (Exception ex) {
                // log and return a friendly message
                // logger.error("deleteEmployee failed", ex);
                return "Unexpected error while deleting employee " + empId + ".";
            }
        }
        return "Employee Not Found For Id " + empId;
    }

    private void handleDriverLicence(PlantEmployee employee) {
        if (!"Driver".equalsIgnoreCase(employee.getDesignation())) {
            employee.setLicenceType(null);
            employee.setLicenceNumber(null);
            employee.setLicenceIssueDate(null);
            employee.setLicenceExpiryDate(null);
        }
    }

}