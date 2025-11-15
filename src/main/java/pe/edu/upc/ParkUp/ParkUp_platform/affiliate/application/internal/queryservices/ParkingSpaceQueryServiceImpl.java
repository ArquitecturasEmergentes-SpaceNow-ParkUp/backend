package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingSpaceRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ParkingSpaceQueryServiceImpl implements ParkingSpaceQueryService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceQueryServiceImpl(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = Objects.requireNonNull(parkingSpaceRepository);
    }

    @Override
    public List<ParkingSpace> getByMapId(Long mapId) {
        return parkingSpaceRepository.findByMapId(mapId);
    }
}

