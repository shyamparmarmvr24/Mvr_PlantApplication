package com.mvr.plant.DTO;
import com.mvr.plant.entity.PlantOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantOperationDTO
{
    private Long plantId;
    private PlantOperation operation;
}
