//PlantOperation
package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Operation_Table")
@AllArgsConstructor
@NoArgsConstructor
public class PlantOperation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_seq")
    @SequenceGenerator(
            name = "operation_seq",
            sequenceName = "OperationSeq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long operationID;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    private LocalDate operationDate;

    private Double sludgeTankLevel;

    private Double sludgeProcessed;

    private Double sludgeReceived;

    private Double biocharProduced;

    private Double plantRunningHrs;

    private Double solarReadingAm;

    private Double solarReadingPm;

    private Double powerReadingAm;

    private Double powerReadingPm;

    private Integer noOfTrips;

    private Double vehicleReadingAm;

    private Double vehicleReadingPm;

    private Boolean dgUsed;

    private Double dgRunHrs;

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
