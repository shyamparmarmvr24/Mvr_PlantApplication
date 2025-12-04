//FstpPlant
package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "FSTP_PLANT")
@AllArgsConstructor
@NoArgsConstructor
public class FstpPlant
{
    @Id
    @Column(name = "PlantID",nullable = false, unique = true)
    private Long plantID;

    @Column(name = "PlantName",nullable = false,length = 50)
    private String plantName;

    @Column(name = "StateCode",length = 10)
    private String stateCode;

    @Column(name = "District",length = 50)
    private String district;

    @Column(name = "KLD",nullable = false)
    private Long kld;

    @Column(name = "WardNo")
    private Integer wardNo;

    @Column(name = "PinCode")
    private Long pinCode;

    @Column(name = "Zones",nullable = false)
    private Integer zones;

    @Column(name = "MNIT", columnDefinition = "BIT DEFAULT 0",nullable = false)
    private Boolean mnit = false;

    @Column(name = "PermanentPower", columnDefinition = "BIT DEFAULT 0",nullable = false)
    private Boolean permanentPower = false;

    @Column(name = "Solar", columnDefinition = "BIT DEFAULT 0",nullable = false)
    private Boolean solar = false;

    @Column(name = "Internet", columnDefinition = "BIT DEFAULT 0",nullable = false)
    private Boolean internet = false;

    @Column(name = "MnitDateOfCompletion")
    private LocalDate mnitDateOfCompletion;

    @Column(name = "PermanentPowerDateOfCompletion")
    private LocalDate permanentPowerDateOfCompletion;

    @Column(name = "InternetDateOfCompletion")
    private LocalDate internetDateOfCompletion;

    @Column(name = "SolarDateOfCompletion")
    private LocalDate solarDateOfCompletion;

    @Column(name = "SolarPlantCapacity")
    private Long solarPlantCapacity;

    @Column(name = "ConstructionStartedDate")
    private LocalDate constructionStartedDate;

    @Column(name = "CivilWorkCompletedDate")
    private LocalDate civilWorkCompletedDate;

    @Column(name = "MachinaryAssembleDate")
    private LocalDate machinaryAssembleDate;

    @Column(name = "CodAndBodSenserDate")
    private LocalDate codAndBodSenserDate;

    @Column(name = "IpPhoneDate")
    private LocalDate ipPhoneDate;

    @Column(name = "CameraConfigurationDate")
    private LocalDate cameraConfigurationDate;

    @Column(name = "Tabs")
    private Boolean tabs;

    @Column(name = "TabsReceivedDate")
    private LocalDate tabsReceivedDate;

    @Column(name = "Gps")
    private Boolean gps;

    @Column(name = "GpsDate")
    private LocalDate gpsDate;

    @Column(name = "NoOfVehicle")
    private Integer noOfVehicle;

    @Column(name = "NoOfEmployees")
    private Integer noOfEmployees;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlantOperation> operations = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LaboratoryOperation> labOperations = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlantEmployee> plantEmployees = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VehicleInformation> vehicleInfo = new ArrayList<>();
}
