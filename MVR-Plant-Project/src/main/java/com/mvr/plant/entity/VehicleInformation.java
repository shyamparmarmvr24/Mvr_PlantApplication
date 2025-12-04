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
@Table(name = "Vehicle_Information_Table")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleInformation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq")
    @SequenceGenerator(
            name = "vehicle_seq",
            sequenceName = "VehicleSeq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long vehicleID;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    @Column(nullable = false,length = 50)
    private String vehicleModelName;

    @Column(nullable = false, unique = true, length = 30)
    private String vehicleNumber;

    @Column(nullable = false, unique = true, length = 50)
    private String vehicleChassisNo;

    @Column(nullable = false)
    private LocalDate dateOfRegistration;

    private LocalDate insuranceDate;

    private LocalDate insuranceExpiryDate;

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
