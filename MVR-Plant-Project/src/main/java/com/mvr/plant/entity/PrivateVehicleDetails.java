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
@Table(name = "PrivateVehicleDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateVehicleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "private_vehicle_seq")
    @SequenceGenerator(
            name = "private_vehicle_seq",
            sequenceName = "PrivateVehicleSeq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long privateVehicleID;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    private String privateVehNumber;

    private Long driverNumber;

    private String driverName;

    private Double receivedSludgeLtrs;

    private Double receivedSludgeKgs;

    private LocalDate operationDate;

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
