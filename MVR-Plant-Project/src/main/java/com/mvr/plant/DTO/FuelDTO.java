package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelDTO
{
    private Long plantId;
    private Long vehicleId;
    private Boolean lastFuelFilled;
    private LocalDate lastFuelFilledDate;
    private Double filledLiters;
    private Double currentOdometerReading;
}
