package pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;

import java.util.List;
import java.util.Optional;

/**
 * User device repository interface
 * Provides data access methods for UserDevice entity
 */
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    
    /**
     * Finds all devices for a specific user
     *
     * @param userId The user ID
     * @return List of user devices
     */
    List<UserDevice> findByUserId(Long userId);
    
    /**
     * Finds active devices for a specific user
     *
     * @param userId   The user ID
     * @param isActive The active status
     * @return List of active user devices
     */
    List<UserDevice> findByUserIdAndIsActive(Long userId, Boolean isActive);
    
    /**
     * Finds a device by its token
     *
     * @param deviceToken The device token
     * @return Optional user device
     */
    Optional<UserDevice> findByDeviceToken(String deviceToken);
    
    /**
     * Checks if a device token already exists
     *
     * @param deviceToken The device token
     * @return true if token exists
     */
    boolean existsByDeviceToken(String deviceToken);
}
