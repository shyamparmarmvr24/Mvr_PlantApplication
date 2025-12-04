package com.mvr.plant.repository;

import com.mvr.plant.entity.LaboratoryOperation;
import com.mvr.plant.entity.VehicleInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IVehicleRepo extends JpaRepository<VehicleInformation,Long>
{
    @Query("SELECT o FROM VehicleInformation o WHERE o.plant.plantID = :plantId")
    List<VehicleInformation> getVehicleInformationByPlantId(@Param("plantId") Long plantId);

    @Query("SELECT v FROM VehicleInformation v WHERE v.vehicleID = :vehicleId AND v.plant.plantID = :plantId")
    Optional<VehicleInformation> findByPlantIdAndVehicleId(
            @Param("plantId") Long plantId,
            @Param("vehicleId") Long vehicleId
    );

}
