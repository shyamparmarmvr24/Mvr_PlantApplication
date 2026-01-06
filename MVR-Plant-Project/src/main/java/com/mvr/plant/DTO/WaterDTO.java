package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterDTO
{
    private Long plantId;
    private Boolean waterUsed;
    private LocalDate waterFilledDate;
    private Double waterLtrs;
    private Double totalWaterAmount;
}
