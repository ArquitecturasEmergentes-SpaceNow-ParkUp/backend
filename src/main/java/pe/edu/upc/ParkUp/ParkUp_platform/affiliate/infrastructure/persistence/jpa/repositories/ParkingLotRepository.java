package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}