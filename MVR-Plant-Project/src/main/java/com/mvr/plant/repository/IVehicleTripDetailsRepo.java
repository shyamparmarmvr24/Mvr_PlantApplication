package com.mvr.plant.repository;

import com.mvr.plant.entity.VehicleTripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
