package com.mvr.plant.repository;

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

//    @Query("SELECT o FROM PlantOperation o WHERE o.plant.plantID = :plantId")
//    Optional<PlantOperation> getOperationByPlantId(@Param("plantId") Long plantId);

    //date sorting retrieve by plantid and date
    @Query("SELECT o FROM PlantOperation o WHERE o.plant.plantID = :plantId AND o.operationDate = :date")
    Optional<PlantOperation> getOperationByPlantIdAndDate(@Param("plantId") Long plantId,
                                                          @Param("date") LocalDate date);

    @Query("SELECT p FROM FstpPlant p WHERE p.plantID = :plantID")
    Optional<FstpPlant> findPlantByPlantID(@Param("plantID") Long plantID);

    @Query("SELECT o FROM PlantOperation o WHERE o.operationDate = :date")
    List<PlantOperation> getAllOperationByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"plant"})
    List<PlantOperation> findByOperationDateBetween(LocalDate startDate, LocalDate endDate);

//    @Query("""
//        SELECT p FROM PlantOperation p
//        WHERE p.plant.plantID = :plantId
//          AND p.powerBill = true
//          AND p.lastBillDate IS NOT NULL
//        ORDER BY p.lastBillDate DESC
//    """)
//    List<PlantOperation> findLatestPowerBill(@Param("plantId") Long plantId);
//
//    @Query("""
//    SELECT p FROM PlantOperation p
//    WHERE p.plant.plantID = :plantId
//      AND p.waterUsed = true
//      AND p.waterFilledDate IS NOT NULL
//    ORDER BY p.waterFilledDate DESC
//    """)
//    List<PlantOperation> findLatestWaterFilled(@Param("plantId") Long plantId);

//    @Query("""
//    SELECT p FROM PlantOperation p
//    WHERE p.plant.plantID = :plantId
//      AND p.powerBill = true
//      AND p.lastBillDate IS NOT NULL
//      AND p.operationDate <= :selectedDate
//    ORDER BY p.operationDate DESC
//    """)
//    List<PlantOperation> findLatestPowerBillTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);
//
//    @Query("""
//    SELECT p FROM PlantOperation p
//    WHERE p.plant.plantID = :plantId
//      AND p.waterUsed = true
//      AND p.waterFilledDate IS NOT NULL
//      AND p.operationDate <= :selectedDate
//    ORDER BY p.operationDate DESC
//    """)
//    List<PlantOperation> findLatestWaterFilledTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

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
        p.totalWaterAmount
    )
    FROM PlantOperation p
    WHERE p.plant.plantID = :plantId
      AND p.waterUsed = true
      AND p.waterFilledDate IS NOT NULL
      AND p.operationDate <= :selectedDate
    ORDER BY p.operationDate DESC
    """)
    List<WaterDTO> findLatestWaterFilledTillDate(@Param("plantId") Long plantId, @Param("selectedDate") LocalDate selectedDate);

//    @Query("""
//    SELECT new com.mvr.plant.DTO.WaterDTO(
//        p.plant.plantID,
//        p.waterUsed,
//        p.waterFilledDate,
//        p.waterLtrs,
//        p.totalWaterAmount
//    )
//    FROM PlantOperation p
//    WHERE p.waterUsed = true
//      AND p.waterFilledDate IS NOT NULL
//    ORDER BY p.waterFilledDate DESC
//    """)
//    List<WaterDTO> findAllWaterDetails();
//
//    @Query("""
//    SELECT new com.mvr.plant.DTO.PowerBillDTO(
//        p.plant.plantID,
//        p.powerBill,
//        p.lastBillDate,
//        p.TotalNoOfUnits,
//        p.totalBillAmount
//    )
//    FROM PlantOperation p
//    WHERE p.powerBill = true
//      AND p.lastBillDate IS NOT NULL
//    ORDER BY p.lastBillDate DESC
//    """)
//    List<PowerBillDTO> findAllPowerBillDetails();

    @Query("""
    SELECT new com.mvr.plant.DTO.WaterDTO(
        p.plant.plantID,
        p.waterUsed,
        p.waterFilledDate,
        p.waterLtrs,
        p.totalWaterAmount
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


}
