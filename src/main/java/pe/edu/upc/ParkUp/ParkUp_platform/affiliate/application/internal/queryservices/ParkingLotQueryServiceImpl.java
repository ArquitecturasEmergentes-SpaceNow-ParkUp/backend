package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetAllParkingLotsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetParkingLotByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingLotRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParkingLotQueryServiceImpl implements ParkingLotQueryService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotQueryServiceImpl(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = Objects.requireNonNull(parkingLotRepository);
    }

    @Override
    public Optional<ParkingLot> handle(GetParkingLotByIdQuery query) {
        Objects.requireNonNull(query, "GetParkingLotByIdQuery no puede ser null");
        Long id = Objects.requireNonNull(query.parkingLotId(), "parkingLotId no puede ser null");
        return parkingLotRepository.findById(id);
    }

    @Override
    public List<ParkingLot> handle(GetAllParkingLotsQuery query) {
        // query puede ser un placeholder; simplemente devolver todos los parking lots
        return parkingLotRepository.findAll();
    }
}