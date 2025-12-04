package com.mvr.plant.service;

import com.mvr.plant.entity.FstpPlant;
import com.mvr.plant.repository.IPlantMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements IPlantService
{
    @Autowired
    private IPlantMgmtRepo plantRepo;

    @Override
    public FstpPlant insertPlantDetails(FstpPlant plant) {
        return plantRepo.insertPlantDetails(plant);
    }

    @Override
    public List<FstpPlant> getAllPlants() {
        return plantRepo.getAllPlants();
    }

    @Override
    public FstpPlant getPlantById(Long id) {
        return plantRepo.getPlantById(id);
    }

    @Override
    public String updatePlantDetails(Long id, FstpPlant plant) {
        return plantRepo.updatePlantDetails(id,plant);
    }

    @Override
    public List<FstpPlant> getPlantsByZone(Integer zone) {
        return plantRepo.getPlantsByZone(zone);
    }
}
