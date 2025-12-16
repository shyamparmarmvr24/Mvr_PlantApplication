package com.mvr.plant.repository;

import com.mvr.plant.entity.PlantOperation;
import com.mvr.plant.entity.PrivateVehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPrivateVehicleRepo extends JpaRepository<PrivateVehicleDetails,Long>
{
    @Query("SELECT o FROM PrivateVehicleDetails o WHERE o.plant.plantID = :plantId AND o.operationDate = :date")
    List<PrivateVehicleDetails> getPrivateVehicleByPlantIdAndDate(@Param("plantId") Long plantId,
                                                             @Param("date") LocalDate date);
}
