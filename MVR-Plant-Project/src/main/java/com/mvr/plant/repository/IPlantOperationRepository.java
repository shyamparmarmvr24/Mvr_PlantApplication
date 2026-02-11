package com.mvr.plant.repository;
import com.mvr.plant.DTO.PilletsDTO;
import com.mvr.plant.DTO.PolymerDTO;
import com.mvr.plant.DTO.PowerBillDTO;
import com.mvr.plant.DTO.WaterDTO;
import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.entity.PlantOperation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPlantOperationRepository extends JpaRepository<PlantOperation,Long>
{
    //date sorting retrieve by plantid and date
    @Query("SELECT o FROM PlantOperation o WHERE o.plant.plantID = :plantId AND o.operationDate = :date")
    Optional<PlantOperation> getOperationByPlantIdAndDate(@Param("plantId") Long plantId, @Param("date") LocalDate date);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

    @Query("SELECT o FROM PlantOperation o WHERE o.operationDate = :date")
    List<PlantOperation> getAllOperationByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"plant"})
    List<PlantOperation> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.PowerBillDTO(
        p.plant.plantID,
        p.powerBill,
        p.lastBillDate,
        p.TotalNoOfUnits,
        p.totalBillAmount
    )
    FROM PlantOperation p
    WHERE p.plant.plantID = :plantId
      AND p.powerBill = true
      AND p.lastBillDate IS NOT NULL
      AND p.operationDate <= :selectedDate
    ORDER BY p.operationDate DESC
    """)
    List<PowerBillDTO> findLatestPowerBillTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.WaterDTO(
        p.plant.plantID,
        p.waterUsed,
        p.waterFilledDate,
        p.waterLtrs,
        p.totalWaterAmount,
        p.waterRemark
    )
    FROM PlantOperation p
    WHERE p.plant.plantID = :plantId
      AND p.waterUsed = true
      AND p.waterFilledDate IS NOT NULL
      AND p.operationDate <= :selectedDate
    ORDER BY p.operationDate DESC
    """)
    List<WaterDTO> findLatestWaterFilledTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.WaterDTO(
        p.plant.plantID,
        p.waterUsed,
        p.waterFilledDate,
        p.waterLtrs,
        p.totalWaterAmount,
        p.waterRemark
    )
    FROM PlantOperation p
    WHERE p.waterUsed = true
      AND p.waterFilledDate IS NOT NULL
      AND p.plant.plantID = :plantId
    ORDER BY p.waterFilledDate DESC
""")
    List<WaterDTO> findWaterDetailsByPlantId(@Param("plantId") Long plantId);

    @Query("""
    SELECT new com.mvr.plant.DTO.PowerBillDTO(
        p.plant.plantID,
        p.powerBill,
        p.lastBillDate,
        p.TotalNoOfUnits,
        p.totalBillAmount
    )
    FROM PlantOperation p
    WHERE p.powerBill = true
      AND p.lastBillDate IS NOT NULL
      AND p.plant.plantID = :plantId
    ORDER BY p.lastBillDate DESC
""")
    List<PowerBillDTO> findPowerBillDetailsByPlantId(@Param("plantId") Long plantId);


    @Query("""
    SELECT new com.mvr.plant.DTO.PolymerDTO(
        p.plant.plantID,
        p.isPolymerStockReceived,
        p.ploymerStockReceivedDate,
        p.ploymerStockQuantity
    )
    FROM PlantOperation p
    WHERE p.plant.plantID = :plantId
      AND p.isPolymerStockReceived = true
      AND p.ploymerStockReceivedDate IS NOT NULL
      AND p.operationDate <= :selectedDate
    ORDER BY p.operationDate DESC
    """)
    List<PolymerDTO> findLatestPolymerStockTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.PolymerDTO(
        p.plant.plantID,
        p.isPolymerStockReceived,
        p.ploymerStockReceivedDate,
        p.ploymerStockQuantity
    )
    FROM PlantOperation p
    WHERE p.isPolymerStockReceived = true
      AND p.ploymerStockReceivedDate IS NOT NULL
      AND p.plant.plantID = :plantId
    ORDER BY p.ploymerStockReceivedDate DESC
""")
    List<PolymerDTO> findPolymerStockByPlantId(@Param("plantId") Long plantId);

    @Query("""
    SELECT new com.mvr.plant.DTO.PilletsDTO(
        p.plant.plantID,
        p.isPilletsStockReceived,
        p.pilletsStockReceivedDate,
        p.pilletsStockQuantity
    )
    FROM PlantOperation p
    WHERE p.plant.plantID = :plantId
      AND p.isPilletsStockReceived = true
      AND p.pilletsStockReceivedDate IS NOT NULL
      AND p.operationDate <= :selectedDate
    ORDER BY p.operationDate DESC
    """)
    List<PilletsDTO> findLatestPilletsStockTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

    @Query("""
    SELECT new com.mvr.plant.DTO.PilletsDTO(
        p.plant.plantID,
        p.isPilletsStockReceived,
        p.pilletsStockReceivedDate,
        p.pilletsStockQuantity
    )
    FROM PlantOperation p
    WHERE p.isPilletsStockReceived = true
      AND p.pilletsStockReceivedDate IS NOT NULL
      AND p.plant.plantID = :plantId
    ORDER BY p.pilletsStockReceivedDate DESC
""")
    List<PilletsDTO> findPilletsStockByPlantId(@Param("plantId") Long plantId);

}
