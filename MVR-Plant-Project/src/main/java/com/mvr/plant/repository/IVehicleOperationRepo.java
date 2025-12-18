package com.mvr.plant.repository;

import com.mvr.plant.entity.VehicleOperation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVehicleOperationRepo extends JpaRepository<VehicleOperation,Long>
{
    @Query("SELECT o FROM VehicleOperation o WHERE o.vehicle.vehicleID = :vehicleID AND o.operationDate = :date")
    Optional<VehicleOperation> getVehicleOperationByVehicleIdAndDate(@Param("vehicleID") Long vehicleID, @Param("date") LocalDate date);

    @Query("SELECT vo FROM VehicleOperation vo " + "JOIN FETCH vo.vehicle v " + "JOIN FETCH v.plant p " + "WHERE vo.operationDate = :date")
    List<VehicleOperation> findByOperationDateWithVehicleAndPlant(@Param("date") LocalDate date);

    @Query("SELECT v FROM VehicleOperation v WHERE v.vehicle.plant.plantID = :plantId AND v.operationDate = :date")
    List<VehicleOperation> findByPlantIdAndDate(Long plantId, LocalDate date);

    @EntityGraph(attributePaths = {"vehicle", "vehicle.plant"})
    List<VehicleOperation> findByOperationDateBetween(LocalDate start, LocalDate end);

    @Query("""
    SELECT v FROM VehicleOperation v
    WHERE v.vehicle.vehicleID = :vehicleId
      AND v.lastFuelFilled = true
      AND v.lastFuelFilledDate IS NOT NULL
    ORDER BY v.lastFuelFilledDate DESC
    """)
    List<VehicleOperation> findLatestFuelFilled(@Param("vehicleId") Long vehicleId);
}
