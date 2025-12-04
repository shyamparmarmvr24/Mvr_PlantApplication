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
@RequiredArgsConstructor
public class FstpPlant
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    @SequenceGenerator(
            name = "plant_seq",
            sequenceName = "PlantSeq",
            allocationSize = 1,
            initialValue = 1
    )
    @Column(name = "SerialNo")
    private Long serialNo;

    @NonNull
    @Column(name = "PlantID",nullable = false, unique = true)
    private Long plantID;
    @NonNull
    @Column(name = "PlantName",length = 50)
    private String plantName;
    @NonNull
    @Column(name = "StateCode",length = 10)
    private String stateCode;
    @NonNull
    @Column(name = "District",length = 50)
    private String district;
    @NonNull
    @Column(name = "KLD")
    private Long kld;
    @NonNull
    @Column(name = "WardNo")
    private Integer wardNo;
    @NonNull
    @Column(name = "PinCode")
    private Long pinCode;
    @NonNull
    @Column(name = "Zones")
    private Integer zones;

    @Column(name = "MNIT", columnDefinition = "BIT DEFAULT 0")
    private Boolean mnit = false;

    @Column(name = "PermanentPower", columnDefinition = "BIT DEFAULT 0")
    private Boolean permanentPower = false;

    @Column(name = "Solar", columnDefinition = "BIT DEFAULT 0")
    private Boolean solar = false;

    @Column(name = "Internet", columnDefinition = "BIT DEFAULT 0")
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
