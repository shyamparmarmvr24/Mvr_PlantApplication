package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Motor_Details")
@AllArgsConstructor
@NoArgsConstructor
public class MotorDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motor_seq")
    @SequenceGenerator(
            name = "motor_seq",
            sequenceName = "MotorSeq",
            allocationSize = 1,
            initialValue = 100
    )
    private Long motorId;

    @ManyToOne
    @JoinColumn(name = "PlantID", referencedColumnName = "PlantID")
    @JsonBackReference
    private FstpPlant plant;

    @Column(name = "MotorModelName",length = 50)
    private String motorModelName;

    @Column(name = "MotorType",length = 50)
    private String motorType;

    @Column(name = "MotorSerialNo",length = 50)
    private String motorSerialNo;

    @Column(name = "MotorMake",length = 50)
    private String motorMake;

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
