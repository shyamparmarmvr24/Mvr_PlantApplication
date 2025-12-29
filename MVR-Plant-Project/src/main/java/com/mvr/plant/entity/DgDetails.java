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

@Data
@Entity
@Table(name = "Dg_Details")
@AllArgsConstructor
@NoArgsConstructor
public class DgDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Dg_seq")
    @SequenceGenerator(
            name = "Dg_seq",
            sequenceName = "DGSeq",
            allocationSize = 1,
            initialValue = 1000
    )
    private Long dgId;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    @Column(name = "DgCompanyName",length = 50)
    private String dgCompanyName;

    @Column(name = "DgPurchaseDate")
    private LocalDate dgPurchaseDate;

    @Column(name = "DgSerialNo",length = 50)
    private String dgSerialNo;

    @Column(name = "DgFrameNo",length = 50)
    private String dgFrameNo;

    @Column(name = "DgBatteryMake",length = 50)
    private String dgBatteryMake;

    @Column(name = "DgBatteryPurchaseDate")
    private LocalDate dgBatteryPurchaseDate;

    @Column(name = "DgBatteryNo",length = 50)
    private String dgBatteryNo;

    @Column(name = "DgBatteryExpiryDate")
    private LocalDate dgBatteryExpiryDate;

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
