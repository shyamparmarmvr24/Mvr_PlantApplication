package com.mvr.plant.repository;

import com.mvr.plant.entity.FstpPlant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PlantMgmtRepoImpl implements IPlantMgmtRepo
{
    @Autowired
    private IPlantRepository plantRepo;

    @Override
    public String insertPlantDetails(FstpPlant plant)
    {
        return "Plant Information Successfully Saved Having Id "+plantRepo.save(plant).getPlantID();
    }

    @Override
    public List<FstpPlant> getAllPlants()
    {
        return plantRepo.findAll();
    }

    @Override
    public FstpPlant getPlantById(Long id)
    {
        return plantRepo.findPlantByPlantID(id).orElseThrow(()->new IllegalStateException("Invalid Plant Id"));
    }

    @Override
    public String updatePlantDetails(Long id, FstpPlant plant)
    {
        FstpPlant existingPlant = plantRepo.findById(id).orElseThrow(() -> new IllegalStateException("Invalid Plant Id"));
        BeanUtils.copyProperties(plant, existingPlant, "plantID","serialNo");
        plantRepo.save(existingPlant);
        return "Plant Details Is Updated Successfully Having Id "+id;
    }

    @Override
    public List<FstpPlant> getPlantsByZone(Integer zone)
    {
        return plantRepo.getPlantsByZone(zone);
    }
}
