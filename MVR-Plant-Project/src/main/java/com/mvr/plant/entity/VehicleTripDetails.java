package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Vehicle_Trip_Details")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_trips_seq")
    @SequenceGenerator(
            name = "vehicle_trips_seq",
            sequenceName = "VehicleTripsSeq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long vehicleTripId;

    @ManyToOne
    @JoinColumn(name = "vehicleOpID", referencedColumnName = "vehicleOpID")
    @JsonBackReference
    private VehicleOperation vehicleOp;

    private Double sludgeCollectLtrs;

    private Double sludgeCollectKgs;

    private LocalTime tripTime;

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
