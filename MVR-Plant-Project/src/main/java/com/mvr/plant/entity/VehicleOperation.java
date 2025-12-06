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
@Table(name = "Vehicle_Operation_Table")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleOperation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_op_seq")
    @SequenceGenerator(
            name = "vehicle_op_seq",
            sequenceName = "VehicleOpSeq",
            allocationSize = 1,
            initialValue = 100
    )
    private Long vehicleOpID;

    @ManyToOne
    @JoinColumn(name = "vehicleID", referencedColumnName = "vehicleID")
    @JsonBackReference
    private VehicleInformation vehicle;

    private LocalDate operationDate;

    private Double vehicleReadingAm;

    private Double vehicleReadingPm;

    private Double vehicleFuelLevel;

    private Boolean lastFuelFilled;

    private LocalDate lastFuelFilledDate;

    private Double filledLiters;

    private Integer noOfTrips;

    private Double sludgeCollect;

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
