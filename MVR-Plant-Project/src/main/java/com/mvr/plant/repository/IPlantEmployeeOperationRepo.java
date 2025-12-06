package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeOperation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPlantEmployeeOperationRepo extends JpaRepository<PlantEmployeeOperation,Long>
{
    //date sorting retrieve by plantid and date
    @Query("SELECT o FROM PlantEmployeeOperation o WHERE o.employee.employeeId = :employeeId AND o.operationDate = :date")
    Optional<PlantEmployeeOperation> getEmployeeOperationByEmployeeIdAndDate(@Param("employeeId") Integer employeeId,
                                                                  @Param("date") LocalDate date);

    @Query("SELECT o FROM PlantEmployeeOperation o WHERE o.employee.employeeId = :employeeId ORDER BY o.operationDate DESC")
    List<PlantEmployeeOperation> getAllOperationsByEmployeeId(@Param("employeeId") Integer employeeId);

    // Fetch employee ops for a date and fetch employee+plant eagerly to avoid N+1
    @Query("SELECT op FROM PlantEmployeeOperation op " + "JOIN FETCH op.employee e " + "JOIN FETCH e.plant p " + "WHERE op.operationDate = :date")
    List<PlantEmployeeOperation> getAllOperationByDate(@Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlantEmployeeOperation op WHERE op.employee.employeeId = :empId")
    void deleteByEmployeeId(@Param("empId") Integer empId);

    @EntityGraph(attributePaths = {"employee", "employee.plant"})
    List<PlantEmployeeOperation> findByOperationDateBetween(LocalDate start, LocalDate end);


}
