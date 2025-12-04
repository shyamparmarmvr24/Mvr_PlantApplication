package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Plant_Employee_Operation_Table")
@AllArgsConstructor
@NoArgsConstructor
public class PlantEmployeeOperation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_op_seq")
    @SequenceGenerator(
            name = "employee_op_seq",
            sequenceName = "EmployeeOpSeq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long operationId;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    @JsonBackReference
    private PlantEmployee employee;

    @Column(nullable = false)
    private Boolean attendanceAm;

    @Column(nullable = false)
    private Boolean attendancePm;

    private LocalDate operationDate;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "Created_Date")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "Updated_Date")
    private LocalDateTime updatedAt;


    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
