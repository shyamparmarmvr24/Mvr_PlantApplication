package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilletsDTO
{
    private Long plantId;
    private Boolean isPilletsStockReceived;
    private LocalDate pilletsStockReceivedDate;
    private Double pilletsStockQuantity;
}
