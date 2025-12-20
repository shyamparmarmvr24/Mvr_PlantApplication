package com.mvr.plant.repository;
import com.mvr.plant.DTO.VehicleTripDetailsDTO;
import com.mvr.plant.entity.VehicleOperation;
import com.mvr.plant.entity.VehicleTripDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class VehicleTripDetailsRepoImpl implements IVehicleTripDetailsRepoMgmt
{
    @Autowired
    private IVehicleTripDetailsRepo tripRepo;
    @Autowired
    private IVehicleOperationRepo operationRepo;

    @Override
    public VehicleTripDetails updateVehicle(Long vehicleOpId, VehicleTripDetails tripDet)
    {
        VehicleOperation operation = operationRepo.findById(vehicleOpId)
                .orElseThrow(() -> new RuntimeException("Vehicle operation not found"));

        //UPDATE
        if (tripDet.getVehicleTripId() != null) {
            VehicleTripDetails existingTrip = tripRepo.findById(tripDet.getVehicleTripId())
                    .orElseThrow(() -> new RuntimeException("Trip not found"));

            existingTrip.setSludgeCollectLtrs(tripDet.getSludgeCollectLtrs());
            existingTrip.setSludgeCollectKgs(tripDet.getSludgeCollectKgs());
            existingTrip.setTripTime(tripDet.getTripTime());

            return tripRepo.save(existingTrip);
        }
        //CREATE
        tripDet.setVehicleOp(operation);
        return tripRepo.save(tripDet);
    }

    @Override
    public List<VehicleTripDetailsDTO> getVehicleTripDetails(Long vehicleOpId)
    {
        return tripRepo.findVehicleTripByVehicleOperation(vehicleOpId).stream().map(t -> new VehicleTripDetailsDTO(
                        t.getVehicleTripId(), t.getSludgeCollectLtrs(), t.getSludgeCollectKgs(), t.getTripTime())).toList();
    }
}