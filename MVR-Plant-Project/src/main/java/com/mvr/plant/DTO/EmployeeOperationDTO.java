package com.mvr.plant.DTO;

import com.mvr.plant.entity.PlantEmployeeOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOperationDTO
{
    private Long plantId;
    private Integer employeeId;
    private String employeeName;
    private String desination;
    private PlantEmployeeOperation plantOp;
}
