package com.mvr.plant.repository;

import com.mvr.plant.entity.DgDetails;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IDgDetailsRepo extends JpaRepository<DgDetails,Long>
{
    @Query("SELECT o FROM DgDetails o WHERE o.plant.plantID = :plantId")
    Optional<DgDetails> getDgDetailsByPlantId(@Param("plantId") Long plantId);

}
