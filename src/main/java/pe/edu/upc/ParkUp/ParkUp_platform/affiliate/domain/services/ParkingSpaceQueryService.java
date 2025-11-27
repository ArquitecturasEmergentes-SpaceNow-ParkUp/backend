package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.List;

public interface ParkingSpaceQueryService {
    List<ParkingSpace> getByMapId(Long mapId);

    java.util.Optional<ParkingSpace> getById(Long id);
}
