//FstpPlant
package com.mvr.plant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlantOperation> operations = new ArrayList<>();
}
