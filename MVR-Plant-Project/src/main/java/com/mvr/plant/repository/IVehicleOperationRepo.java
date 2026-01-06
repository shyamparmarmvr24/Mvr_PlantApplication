package com.mvr.plant.repository;

import com.mvr.plant.DTO.FuelDTO;
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

//    @Query("""
//    SELECT v FROM VehicleOperation v
//    WHERE v.vehicle.vehicleID = :vehicleId
//      AND v.lastFuelFilled = true
//      AND v.lastFuelFilledDate IS NOT NULL
//    ORDER BY v.lastFuelFilledDate DESC
//    """)
//    List<VehicleOperation> findLatestFuelFilled(@Param("vehicleId") Long vehicleId);

//    @Query("""
//    SELECT v FROM VehicleOperation v
//    WHERE v.vehicle.vehicleID = :vehicleId
//      AND v.lastFuelFilled = true
//      AND v.lastFuelFilledDate IS NOT NULL
//      AND v.operationDate <= :selectedDate
//    ORDER BY v.operationDate DESC
//    """)
//    List<VehicleOperation> findLatestFuelFilledTillDate(@Param("vehicleId") Long vehicleId, @Param("selectedDate") LocalDate selectedDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.FuelDTO(
        v.vehicle.plant.plantID,
        v.vehicle.vehicleID,
        v.lastFuelFilled,
        v.lastFuelFilledDate,
        v.filledLiters,
        v.currentOdometerReading
    )
    FROM VehicleOperation v
    WHERE v.vehicle.vehicleID = :vehicleId
      AND v.lastFuelFilled = true
      AND v.lastFuelFilledDate IS NOT NULL
      AND v.operationDate <= :selectedDate
    ORDER BY v.operationDate DESC
    """)
    List<FuelDTO> findLatestFuelFilledTillDate(@Param("vehicleId") Long vehicleId, @Param("selectedDate") LocalDate selectedDate);

//    @Query("""
//    SELECT new com.mvr.plant.DTO.FuelDTO(
//        v.vehicle.plant.plantID,
//        v.vehicle.vehicleID,
//        v.lastFuelFilled,
//        v.lastFuelFilledDate,
//        v.filledLiters,
//        v.currentOdometerReading
//    )
//    FROM VehicleOperation v
//    WHERE v.lastFuelFilled = true
//      AND v.lastFuelFilledDate IS NOT NULL
//    ORDER BY v.lastFuelFilledDate DESC
//    """)
//    List<FuelDTO> findAllFuelDetails();

    @Query("""
    SELECT new com.mvr.plant.DTO.FuelDTO(
        v.vehicle.plant.plantID,
        v.vehicle.vehicleID,
        v.lastFuelFilled,
        v.lastFuelFilledDate,
        v.filledLiters,
        v.currentOdometerReading
    )
    FROM VehicleOperation v
    WHERE v.lastFuelFilled = true
      AND v.lastFuelFilledDate IS NOT NULL
      AND v.vehicle.vehicleID = :vehicleId
    ORDER BY v.lastFuelFilledDate DESC
""")
    List<FuelDTO> findFuelDetailsByVehicleId(@Param("vehicleId") Long vehicleId);


}
