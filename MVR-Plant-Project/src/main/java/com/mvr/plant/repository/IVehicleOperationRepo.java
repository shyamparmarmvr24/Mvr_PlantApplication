package com.mvr.plant.repository;

import com.mvr.plant.entity.PlantEmployeeOperation;
import com.mvr.plant.entity.VehicleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IVehicleOperationRepo extends JpaRepository<VehicleOperation,Long>
{
    @Query("SELECT o FROM VehicleOperation o WHERE o.vehicle.vehicleID = :vehicleID AND o.operationDate = :date")
    Optional<VehicleOperation> getVehicleOperationByVehicleIdAndDate(@Param("vehicleID") Long vehicleID,
                                                                             @Param("date") LocalDate date);
}
