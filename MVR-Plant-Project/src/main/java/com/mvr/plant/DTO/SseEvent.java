package com.mvr.plant.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SseEvent
{
    private String entity;   // PLANT, DG, LAB, VEHICLE, EMPLOYEE
    private String action;   // CREATE, UPDATE, DELETE
    private Long plantId;
    private Long recordId;   // optional (vehicleId, dgId etc)
}
