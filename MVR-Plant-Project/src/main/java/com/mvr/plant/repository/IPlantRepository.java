package com.mvr.plant.repository;


import com.mvr.plant.entity.FstpPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPlantRepository extends JpaRepository<FstpPlant,Long>
{
    @Query("SELECT p FROM FstpPlant p WHERE p.zones = :zone")
    List<FstpPlant> getPlantsByZone(@Param("zone") Integer zone);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

//    @Query("SELECT p.serialNo AS serialNo, p.plantID AS plantID, p.plantName AS plantName, p.kld AS kld FROM FstpPlant p WHERE p.zones = :zone")
//    List<FstpPlant> getPlantsByZone(@Param("zone") Integer zone);
//
//    @Query("SELECT p.serialNo AS serialNo, p.plantID AS plantID, p.plantName AS plantName, p.kld AS kld FROM FstpPlant p WHERE p.plantID = :plantID")
//    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

}
