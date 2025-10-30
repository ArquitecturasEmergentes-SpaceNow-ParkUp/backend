package pe.edu.upc.ParkUp.ParkUp_platform.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;

import java.util.Optional;

/**
 * JPA Repository for UserProfile
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Finds a user profile by user ID
     *
     * @param userId The user ID
     * @return Optional containing the user profile if found
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Finds a user profile by document number (DNI)
     *
     * @param documentNumber The document number
     * @return Optional containing the user profile if found
     */
    Optional<UserProfile> findByDocumentIdDocumentNumber(String documentNumber);

    /**
     * Checks if a user profile exists for a given user ID
     *
     * @param userId The user ID
     * @return True if exists, false otherwise
     */
    boolean existsByUserId(Long userId);

    /**
     * Checks if a user profile exists for a given document number
     *
     * @param documentNumber The document number
     * @return True if exists, false otherwise
     */
    boolean existsByDocumentIdDocumentNumber(String documentNumber);
}
