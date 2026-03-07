package com.mvr.plant.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "FSTP_PLANT")
@AllArgsConstructor
@NoArgsConstructor
public class FstpPlant {
    @Id
    @Column(name = "PlantID", nullable = false, unique = true)
    private Long plantID;

    @Column(name = "PlantName", nullable = false, length = 50)
    private String plantName;

    @Column(name = "StateCode", length = 10)
    private String stateCode;

    @Column(name = "District", length = 50)
    private String district;

    @Column(name = "KLD", nullable = false)
    private Long kld;

    @Column(name = "WardNo")
    private Integer wardNo;

    @Column(name = "PinCode")
    private Long pinCode;

    @Column(name = "Zones", nullable = false)
    private Integer zones;

    @Column(name = "MNIT", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean mnit = false;

    @Column(name = "PermanentPower", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean permanentPower = false;

    @Column(name = "Solar", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean solar = false;

    @Column(name = "Internet", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean internet = false;

    @Column(name = "MnitDateOfCompletion")
    private LocalDate mnitDateOfCompletion;

    @Column(name = "PermanentPowerDateOfCompletion")
    private LocalDate permanentPowerDateOfCompletion;

    @Column(name = "PpMeterSerialNo",length = 50)
    private String ppMeterSerialNo;

    @Column(name = "Category",length = 20)
    private String category;

    @Column(name = "DiscomName",length = 50)
    private String discomName;

    @Column(name = "HeadquarterName",length = 50)
    private String headquarterName;

    @Column(name = "SanctionLoad")
    private Integer sanctionLoad;

    @Column(name = "MultiplicationFactor")
    private Double multiplicationFactor;

    @Column(name = "InternetDateOfCompletion")
    private LocalDate internetDateOfCompletion;

    @Column(name = "SolarDateOfCompletion")
    private LocalDate solarDateOfCompletion;

    @Column(name ="SolarMeterSerialNo",length = 50)
    private String solarMeterSerialNo;

    @Column(name = "SolarPlantCapacity")
    private Long solarPlantCapacity;

    @Column(name = "SolarMultiplicationFactor")
    private Double solarMultiplicationFactor;

    @Column(name = "IsSludgeScreenInstall")
    private Boolean isSludgeScreenInstall;

    @Column(name = "SludgeScreenInstallationDate")
    private LocalDate sludgeScreenInstallationDate;

    @Column(name = "IsGuardRoomPrepared")
    private Boolean isGuardRoomPrepared;

    @Column(name = "GuardRoomPreparedDate")
    private LocalDate guardRoomPreparedDate;

    @Column(name = "IsSolarFencingDone")
    private Boolean isSolarFencingDone;

    @Column(name = "SolarFencingDoneDate")
    private LocalDate solarFencingDoneDate;

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

    @Column(name = "TabImeiNo",length = 70)
    private String tabImeiNo;

    @Column(name = "TabMake",length = 70)
    private String tabMake;

    @Column(name = "CtoCertified")
    private Boolean ctoCertified;

    @Column(name = "CtoIssuedDate")
    private LocalDate ctoIssuedDate;

    @Column(name = "CteCertified")
    private Boolean cteCertified;

    @Column(name = "CteIssuedDate")
    private LocalDate cteIssuedDate;

    @Column(name="WaterType")
    private String waterType;

    @Column(name = "NoOfVehicle")
    private Integer noOfVehicle;

    public int getNoOfEmployees() {
        return plantEmployees != null ? plantEmployees.size() : 0;
    }

    @CreatedDate
    @Column(updatable = false, name = "Created_Date")
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


    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PlantOperation> operations = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LaboratoryOperation> labOperations = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PlantEmployee> plantEmployees = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VehicleInformation> vehicleInfo = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PrivateVehicleDetails> privateVehicle = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DgDetails> dgDetails = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MotorDetails> motorDetails = new ArrayList<>();
}
