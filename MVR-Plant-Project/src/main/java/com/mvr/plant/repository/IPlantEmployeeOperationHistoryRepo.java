package com.mvr.plant.repository;
import com.mvr.plant.DTO.DeletedEmployeeOperationDTO;
import com.mvr.plant.entity.PlantEmployeeOperationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface IPlantEmployeeOperationHistoryRepo extends JpaRepository<PlantEmployeeOperationHistory, Long>
{
    List<PlantEmployeeOperationHistory> findByEmployeeIdAndDateOfJoining(String employeeId, LocalDate dateOfJoining);

    @Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM PlantEmployeeOperationHistory o WHERE o.employeeId = :empId AND o.dateOfJoining = :joiningDate")
    void deleteByEmpIdAndJoiningDate(@Param("empId") String empId, @Param("joiningDate") LocalDate joiningDate);


    @Query("""
    SELECT new com.mvr.plant.DTO.DeletedEmployeeOperationDTO(
        eh.plantId,
        eh.employeeId,
        eh.employeeName,
        eh.designation,
        eh.mobileNo,
        eh.address,
        eh.dateOfBirth,
        eh.dateOfJoining,
        eh.dateOfLeaving,
        oh.id,
        oh.attendanceAm,
        oh.attendancePm,
        oh.operationDate
    )
    FROM PlantEmployeeHistory eh, PlantEmployeeOperationHistory oh
    WHERE eh.employeeId = oh.employeeId
    AND eh.dateOfJoining = oh.dateOfJoining
    AND oh.operationDate BETWEEN :startDate AND :endDate
    AND oh.operationDate >= eh.dateOfJoining
    """)
    List<DeletedEmployeeOperationDTO> getAllEmployeeOperationsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}