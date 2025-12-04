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

    private Double sludgeTankLevelAm;

    private Double sludgeTankLevelPm;

    private Double sludgeReceived;

    private Integer noOfTrips;

    private Double sludgeProcessed;

    private Double biocharProduced;

    private Double plantRunningHrs;

    private Boolean solarIntegration;

    private Double powerReadingAmImport;

    private Double powerReadingAmExport;

    private Double powerReadingPmImport;

    private Double powerReadingPmExport;

    private Double powerReadingAm;

    private Double powerReadingPm;

    private Double powerConsumed;

    private Double dgReadingAm;

    private Double dgReadingPm;

    private Double dgDiesalPercentageAm;

    private Double dgDiesalPercentagePm;

    private Double dgRunHours;

    private Double polymerUsage;

    private Double pillets;

    @Column(length = 500)
    private String remarks;

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
