package com.mvr.plant.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Plant_Employee_History_Table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantEmployeeHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    private Long plantId;

    private String employeeName;
    private String designation;
    private Long mobileNo;
    private Long alternateMobNo;
    private String address;

    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private LocalDate dateOfLeaving;

    private String licenceType;
    private String licenceNumber;
    private LocalDate licenceIssueDate;
    private LocalDate licenceExpiryDate;

    private LocalDateTime archivedAt;
}
