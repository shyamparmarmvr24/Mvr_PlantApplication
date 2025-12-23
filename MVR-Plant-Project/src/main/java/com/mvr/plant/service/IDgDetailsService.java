package com.mvr.plant.service;

import com.mvr.plant.entity.DgDetails;

public interface IDgDetailsService
{
    public DgDetails createDgDetails(Long plantId, DgDetails dg);
    public DgDetails updateDgDetails(Long dgId,DgDetails dg);
    public String deleteDgDetails(Long dgId);
    public DgDetails getDgDetailsByPlantId(Long plantId);
}
