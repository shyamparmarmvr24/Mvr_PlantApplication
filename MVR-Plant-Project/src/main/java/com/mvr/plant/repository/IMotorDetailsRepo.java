package com.mvr.plant.repository;

import com.mvr.plant.entity.MotorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMotorDetailsRepo extends JpaRepository<MotorDetails, Long>
{
    @Query("SELECT o FROM MotorDetails o WHERE o.plant.plantID = :plantId")
    List<MotorDetails> getMotorDetailsByPlantId(@Param("plantId") Long plantId);
}
