package com.mvr.plant.service;

import com.mvr.plant.entity.DgDetails;
import com.mvr.plant.repository.IDgDetailsRepoMgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DgDetailsServiceImpl implements IDgDetailsService
{
    @Autowired
    private IDgDetailsRepoMgmt dgRepo;


    @Override
    public DgDetails createDgDetails(Long plantId, DgDetails dg) {
        return dgRepo.createDgDetails(plantId,dg);
    }

    @Override
    public DgDetails updateDgDetails(Long dgId, DgDetails dg) {
        return dgRepo.updateDgDetails(dgId,dg);
    }

    @Override
    public String deleteDgDetails(Long dgId) {
        return dgRepo.deleteDgDetails(dgId);
    }

    @Override
    public Optional<DgDetails> getDgDetailsByPlantId(Long plantId) {
        return dgRepo.getDgDetailsByPlantId(plantId);
    }
}
