package com.mvr.plant.repository;

import com.mvr.plant.entity.DgDetails;
import com.mvr.plant.entity.FstpPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DgDetailsRepoImpl implements IDgDetailsRepoMgmt
{
    @Autowired
    private IDgDetailsRepo dgRepo;

    @Autowired
    private IPlantRepository plantRepo;

    @Override
    public DgDetails createDgDetails(Long plantId, DgDetails dg)
    {
        // 🔥 PREVENT DUPLICATE DG FOR SAME PLANT
        dgRepo.getDgDetailsByPlantId(plantId).ifPresent(existing -> {
            throw new IllegalStateException("DG already exists for this plant");
        });

        // Fetch Plant
        FstpPlant plant = plantRepo.findPlantByPlantID(plantId).orElseThrow(() -> new RuntimeException("Plant not found: " + plantId));

        // Set FK plant reference
        dg.setPlant(plant);

        // Save employee
        return dgRepo.save(dg);
    }

    @Override
    public DgDetails updateDgDetails(Long dgId, DgDetails dg)
    {
        DgDetails existing = dgRepo.findById(dgId).orElseThrow(() -> new IllegalStateException("Dg Not Found"));
        existing.setDgCompanyName(dg.getDgCompanyName());
        existing.setDgPurchaseDate(dg.getDgPurchaseDate());
        existing.setDgSerialNo(dg.getDgSerialNo());
        existing.setDgFrameNo(dg.getDgFrameNo());
        existing.setDgBatteryMake(dg.getDgBatteryMake());
        existing.setDgBatteryPurchaseDate(dg.getDgBatteryPurchaseDate());
        existing.setDgBatteryNo(dg.getDgBatteryNo());
        existing.setDgBatteryExpiryDate(dg.getDgBatteryExpiryDate());

        return dgRepo.save(existing);
    }

    @Override
    public String deleteDgDetails(Long dgId)
    {
        Optional<DgDetails> byId = dgRepo.findById(dgId);
        if(byId.isPresent())
        {
            dgRepo.deleteById(dgId);
            return "Dg Details Deleted Successfully";
        }

        return "Dg Details Not Found For Id "+dgId;
    }

    @Override
    public Optional<DgDetails> getDgDetailsByPlantId(Long plantId)
    {
        return dgRepo.getDgDetailsByPlantId(plantId);
    }

}
