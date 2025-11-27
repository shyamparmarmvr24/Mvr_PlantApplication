package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;

import java.util.List;

public interface IPlantMgmtRepo
{
    public String insertPlantDetails(FstpPlant plant);
    public List<FstpPlant> getAllPlants();
    public FstpPlant getPlantById(Long id);
    public String updatePlantDetails(Long id,FstpPlant plant);
    public List<FstpPlant> getPlantsByZone(Integer zone);
}
