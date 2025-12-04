package com.mvr.plant.repository;
import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantEmployee;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPlantEmployeeRepo extends JpaRepository<PlantEmployee,Integer>
{
    @Query("SELECT o FROM PlantEmployee o WHERE o.plant.plantID = :plantId")
    List<PlantEmployee> getEmployeesByPlantId(@Param("plantId") Long plantId);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

//    @Query("SELECT p FROM PlantEmployee p WHERE p.plant.plantID = :plantID AND p.employeeId = :employeeId")
//    Optional<PlantEmployee> findEmployeeByPlantAndEmployeeId(@Param("plantID") Long plantID, @Param("employeeId") Integer employeeId);

    @Query("SELECT o FROM PlantEmployee o WHERE o.plant.plantID = :plantId AND o.employeeId = :employeeId")
    Optional<PlantEmployee> getEmployeeByPlantIdAndEmployeeId(
            @Param("plantId") Long plantId,
            @Param("employeeId") Integer employeeId
    );

}
