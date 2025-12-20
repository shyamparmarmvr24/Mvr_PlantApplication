package com.mvr.plant.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripDetailsDTO
{
    private Long vehicleTripId;
    private Double sludgeCollectLtrs;
    private Double sludgeCollectKgs;
    private LocalTime tripTime;
}
