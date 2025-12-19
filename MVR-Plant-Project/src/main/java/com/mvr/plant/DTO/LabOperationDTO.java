package com.mvr.plant.DTO;

import com.mvr.plant.entity.LaboratoryOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabOperationDTO
{
    private Long plantId;
    private LaboratoryOperation labOperation;
}
