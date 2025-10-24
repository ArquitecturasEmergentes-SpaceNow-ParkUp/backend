package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;

import java.util.List;
import java.util.Optional;

/**
 * This interface is responsible for providing the RecognitionUnit entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface RecognitionUnitRepository extends JpaRepository<RecognitionUnit, Long> {

    /**
     * This method is responsible for finding the recognition unit by identifier.
     * @param identifier The identifier.
     * @return The recognition unit object.
     */
    Optional<RecognitionUnit> findByIdentifier(String identifier);

    /**
     * This method is responsible for checking if the recognition unit exists by identifier.
     * @param identifier The identifier.
     * @return True if the recognition unit exists, false otherwise.
     */
    boolean existsByIdentifier(String identifier);

    /**
     * This method is responsible for finding all recognition units by affiliate ID.
     * @param affiliateId The affiliate ID.
     * @return List of recognition units.
     */
    List<RecognitionUnit> findByAffiliateId(Long affiliateId);

    /**
     * This method is responsible for finding all recognition units by parking lot ID.
     * @param parkingLotId The parking lot ID.
     * @return List of recognition units.
     */
    List<RecognitionUnit> findByParkingLotId(Long parkingLotId);

    /**
     * This method is responsible for finding all recognition units by status.
     * @param status The status.
     * @return List of recognition units.
     */
    List<RecognitionUnit> findByStatus(RecognitionUnit.Status status);

    /**
     * This method is responsible for finding all recognition units by location.
     * @param location The location.
     * @return List of recognition units.
     */
    List<RecognitionUnit> findByLocation(RecognitionUnit.Location location);

    /**
     * This method is responsible for finding all recognition units by affiliate ID and location.
     * @param affiliateId The affiliate ID.
     * @param location The location.
     * @return List of recognition units.
     */
    List<RecognitionUnit> findByAffiliateIdAndLocation(Long affiliateId, RecognitionUnit.Location location);

    /**
     * This method is responsible for counting recognition units by affiliate ID.
     * @param affiliateId The affiliate ID.
     * @return Count of recognition units.
     */
    long countByAffiliateId(Long affiliateId);
    Optional<RecognitionUnit> findByIpAddressAndPort(String ipAddress, Integer port);
}
