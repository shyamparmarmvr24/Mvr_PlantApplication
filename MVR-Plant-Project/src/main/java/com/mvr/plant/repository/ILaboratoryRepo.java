package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ILaboratoryRepo extends JpaRepository<LaboratoryOperation,Long>
{
//    @Query("SELECT o FROM LaboratoryOperation o WHERE o.plant.plantID = :plantId")
//    Optional<LaboratoryOperation> getLabOperationByPlantId(@Param("plantId") Long plantId);

    //date sorting retrieve by plantid and date
    @Query("SELECT o FROM LaboratoryOperation o WHERE o.plant.plantID = :plantId AND o.operationDate = :date")
    Optional<LaboratoryOperation> getLabOperationByPlantIdAndDate(@Param("plantId") Long plantId,
                                                          @Param("date") LocalDate date);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

    @Query("SELECT o FROM LaboratoryOperation o WHERE o.operationDate = :date")
    List<LaboratoryOperation> getLabOperationsByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"plant"})
    List<LaboratoryOperation> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);

}
