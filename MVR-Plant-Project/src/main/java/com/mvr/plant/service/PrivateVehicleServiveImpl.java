package com.mvr.plant.service;

import com.mvr.plant.entity.PrivateVehicleDetails;
import com.mvr.plant.repository.IPrivateVehicleRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrivateVehicleServiveImpl implements IPrivateVehicleService
{
    @Autowired
    private IPrivateVehicleRepoImpl privateVehRepo;

    @Override
    public PrivateVehicleDetails createPrivateVehicle(Long plantId, PrivateVehicleDetails privateVehicle) {
        return privateVehRepo.createPrivateVehicle(plantId,privateVehicle);
    }

    @Override
    public List<PrivateVehicleDetails> getPrivateVehicleInformationByPlantIdAndDate(Long plantId, LocalDate date) {
        return privateVehRepo.getPrivateVehicleInformationByPlantIdAndDate(plantId,date);
    }

    @Override
    public PrivateVehicleDetails updateVehicle(Long plantId, Long privateVehicleId, PrivateVehicleDetails updated) {
        return privateVehRepo.updateVehicle(plantId,privateVehicleId,updated);
    }
}
