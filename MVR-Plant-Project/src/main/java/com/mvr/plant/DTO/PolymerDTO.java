package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolymerDTO
{
    private Long plantId;
    private Boolean isPolymerStockReceived;
    private LocalDate ploymerStockReceivedDate;
    private Double ploymerStockQuantity;
}
