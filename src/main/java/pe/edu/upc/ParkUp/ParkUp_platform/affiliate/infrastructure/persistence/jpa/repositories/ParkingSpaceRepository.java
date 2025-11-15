package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    @Query("select ps from ParkingSpace ps where ps.map.id = ?1")
    List<ParkingSpace> findByMapId(Long mapId);

    @Query("select ps from ParkingSpace ps where ps.map.id = ?1 and ps.code = ?2")
    Optional<ParkingSpace> findByMapIdAndCode(Long mapId, String code);
}

