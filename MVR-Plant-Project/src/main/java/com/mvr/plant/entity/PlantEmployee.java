package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Plant_Employee_Table")
@AllArgsConstructor
@NoArgsConstructor
public class PlantEmployee
{
    @Id
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    @Column(nullable = false,length = 60)
    private String employeeName;

    @Column(nullable = false,length = 60)
    private String designation;

    @Column(nullable = false)
    private Long mobileNo;

    private Long alternateMobNo;

    @Column(nullable = false,length = 100)
    private String address;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate dateOfJoining;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonManagedReference
    @JsonIgnore
    private List<PlantEmployeeOperation> plantEmployeesOp = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
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
