package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowerBillDTO
{
    private Long plantId;
    private Boolean powerBill;
    private LocalDate lastBillDate;
    private Double totalNoOfUnits;
    private Double totalBillAmount;
}
