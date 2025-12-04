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
@Table(name = "Laboratory_Table")
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryOperation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laboratory_seq")
    @SequenceGenerator(
            name = "laboratory_seq",
            sequenceName = "LaboratorySeq",
            allocationSize = 1,
            initialValue = 100
    )
    private Long labID;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    private LocalDate operationDate;

    private Double cod;

    private Double bod;

    private Double th;

    private Double temperature;

    private Double tss;

    private Double ph;

    private Double velocity;

    private Double cumulativeFlow;

    private Double flowMeterReadingAm;

    private Double flowMeterReadingPm;


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
