package com.mvr.plant.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Plant_Employee_Operation_History_Table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantEmployeeOperationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    private LocalDate dateOfJoining;

    private LocalDate operationDate;

    private Boolean attendanceAm;
    private Boolean attendancePm;

    private LocalDateTime archivedAt;
}
