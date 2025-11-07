package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.ParkingLotId;

import java.util.List;
import java.util.Optional;

import java.util.Optional;
import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, ParkingLotId> {

    @Override
    Optional<ParkingLot> findById(ParkingLotId id);

    @Override
    List<ParkingLot> findAll();

    @Override
    ParkingLot save(ParkingLot parkingLot);

    @Override
    void deleteById(ParkingLotId id);
}