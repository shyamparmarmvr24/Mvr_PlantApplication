package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IPlantOperationRepository extends JpaRepository<PlantOperation,Long>
{

//    @Query("SELECT o FROM PlantOperation o WHERE o.plant.plantID = :plantId")
//    Optional<PlantOperation> getOperationByPlantId(@Param("plantId") Long plantId);

    //date sorting retrieve by plantid and date
    @Query("SELECT o FROM PlantOperation o WHERE o.plant.plantID = :plantId AND o.operationDate = :date")
    Optional<PlantOperation> getOperationByPlantIdAndDate(@Param("plantId") Long plantId,
                                                          @Param("date") LocalDate date);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

}
