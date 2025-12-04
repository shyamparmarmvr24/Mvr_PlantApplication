package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantEmployeeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
