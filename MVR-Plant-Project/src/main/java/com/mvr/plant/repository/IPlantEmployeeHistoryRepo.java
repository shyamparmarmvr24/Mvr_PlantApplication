package com.mvr.plant.repository;
import com.mvr.plant.entity.PlantEmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPlantEmployeeHistoryRepo extends JpaRepository<PlantEmployeeHistory, Long>
{
    List<PlantEmployeeHistory> findByEmployeeId(String employeeId);

    Optional<PlantEmployeeHistory> findByEmployeeIdAndDateOfJoining(String employeeId, LocalDate dateOfJoining);

    @Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM PlantEmployeeHistory h WHERE h.employeeId = :empId AND h.dateOfJoining = :joiningDate")
    void deleteByEmpIdAndJoiningDate(@Param("empId") String empId, @Param("joiningDate") LocalDate joiningDate);

    @Query("SELECT e FROM PlantEmployeeHistory e WHERE e.plantId = :plantId AND e.dateOfJoining <= :date")
    List<PlantEmployeeHistory> getEmployeesHistoryByPlantIdAndDate(@Param("plantId") Long plantId, @Param("date") LocalDate date);
}