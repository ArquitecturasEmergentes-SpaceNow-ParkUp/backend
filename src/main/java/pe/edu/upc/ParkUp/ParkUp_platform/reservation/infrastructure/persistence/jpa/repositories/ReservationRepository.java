package pe.edu.upc.ParkUp.ParkUp_platform.reservation.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Reservation repository interface
 * Provides data access methods for Reservation aggregate
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    /**
     * Finds all reservations for a specific user
     *
     * @param userId The user ID
     * @return List of reservations
     */
    List<Reservation> findByUserId(Long userId);
    
    /**
     * Finds all reservations with a specific status
     *
     * @param status The reservation status
     * @return List of reservations
     */
    List<Reservation> findByStatus(ReservationStatus status);
    
    /**
     * Finds active or in-progress reservation for a user
     *
     * @param userId The user ID
     * @return Optional reservation
     */
    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId AND r.status IN ('ACTIVE', 'IN_PROGRESS')")
    Optional<Reservation> findActiveReservationByUserId(@Param("userId") Long userId);
    
    /**
     * Finds reservations that are active and start within a time range (for reminders)
     *
     * @param afterTime  Start of time range
     * @param beforeTime End of time range
     * @return List of reservations
     */
    @Query("SELECT r FROM Reservation r WHERE r.status = 'ACTIVE' AND r.startTime > :afterTime AND r.startTime < :beforeTime")
    List<Reservation> findActiveReservationsStartingBetween(
            @Param("afterTime") LocalDateTime afterTime,
            @Param("beforeTime") LocalDateTime beforeTime
    );
    
    /**
     * Finds reservations that are in progress and have exceeded their end time
     *
     * @param currentTime The current time
     * @return List of expired reservations
     */
    @Query("SELECT r FROM Reservation r WHERE r.status = 'IN_PROGRESS' AND r.endTime < :currentTime")
    List<Reservation> findExpiredReservations(@Param("currentTime") LocalDateTime currentTime);
    
    /**
     * Checks if there's a conflicting reservation for a parking lot in a time range
     *
     * @param parkingLotId The parking lot ID
     * @param startTime    Start time of the new reservation
     * @param endTime      End time of the new reservation
     * @return true if there's a conflict
     */
    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.parkingSlotId.parkingLotId = :parkingLotId " +
            "AND r.status IN ('PENDING_PAYMENT', 'ACTIVE', 'IN_PROGRESS') " +
            "AND ((r.startTime < :endTime AND r.endTime > :startTime))")
    boolean existsConflictingReservation(
            @Param("parkingLotId") Long parkingLotId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
