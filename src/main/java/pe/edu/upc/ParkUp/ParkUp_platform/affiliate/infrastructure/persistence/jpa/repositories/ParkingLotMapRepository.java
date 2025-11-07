package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapId;

import java.util.Optional;

@Repository
public interface ParkingLotMapRepository extends JpaRepository<ParkingLotMap, MapId> {

    @Override
    Optional<ParkingLotMap> findById(MapId id);

    @Override
    ParkingLotMap save(ParkingLotMap map);
}