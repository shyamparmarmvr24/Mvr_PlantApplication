package com.mvr.plant.service;

import com.mvr.plant.entity.DgDetails;

import java.util.Optional;

public interface IDgDetailsService
{
    public DgDetails createDgDetails(Long plantId, DgDetails dg);
    public DgDetails updateDgDetails(Long dgId,DgDetails dg);
    public String deleteDgDetails(Long dgId);
    public Optional<DgDetails> getDgDetailsByPlantId(Long plantId);
}
