package com.mvr.plant.repository;

import com.mvr.plant.entity.VehicleTripDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IVehicleTripDetailsRepo extends JpaRepository<VehicleTripDetails,Long>
{
    @Query("""
        SELECT t FROM VehicleTripDetails t
        WHERE t.vehicleOp.vehicleOpID = :vehicleOpId
        ORDER BY t.tripTime
    """)
    List<VehicleTripDetails> findVehicleTripByVehicleOperation(@Param("vehicleOpId") Long vehicleOpId);

    @Modifying
    @Transactional
    @Query("""
           DELETE FROM VehicleTripDetails t
           WHERE t.vehicleOp.vehicle.vehicleID = :vehicleId
           """)
    void deleteTripsByVehicleId(Long vehicleId);
}
