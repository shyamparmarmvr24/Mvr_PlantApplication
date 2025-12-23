package com.mvr.plant.repository;

import com.mvr.plant.entity.DgDetails;

public interface IDgDetailsRepoMgmt
{
    public DgDetails createDgDetails(Long plantId,DgDetails dg);
    public DgDetails updateDgDetails(Long dgId,DgDetails dg);
    public String deleteDgDetails(Long dgId);
    public DgDetails getDgDetailsByPlantId(Long plantId);
}
