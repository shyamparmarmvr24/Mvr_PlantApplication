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

    private Double codReading1;

    private Double codReading2;

    private Double codReading3;

    private Double codReading4;

    private Double bod;

    private Double bodReading1;

    private Double bodReading2;

    private Double bodReading3;

    private Double bodReading4;

    private Double tn;

    private Double thReading1;

    private Double thReading2;

    private Double thReading3;

    private Double thReading4;

    private Double temperature;

    private Double temperatureReading1;

    private Double temperatureReading2;

    private Double temperatureReading3;

    private Double temperatureReading4;

    private Double tss;

    private Double tssReading1;

    private Double tssReading2;

    private Double tssReading3;

    private Double tssReading4;

    private Double ph;

    private Double phReading1;

    private Double phReading2;

    private Double phReading3;

    private Double phReading4 ;

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
