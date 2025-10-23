package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.SessionState;

import java.util.List;

/**
 * This interface is responsible for providing the Ticket entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * This method is responsible for finding all tickets by user ID ordered by creation date descending.
     * @param userId The user ID.
     * @return List of tickets.
     */
    List<Ticket> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * This method is responsible for finding all tickets by license plate ordered by creation date descending.
     * @param licensePlate The license plate.
     * @return List of tickets.
     */
    List<Ticket> findByLicensePlateOrderByCreatedAtDesc(String licensePlate);

    /**
     * This method is responsible for finding all tickets by session state.
     * @param sessionState The session state.
     * @return List of tickets.
     */
    List<Ticket> findBySessionState(SessionState sessionState);

    /**
     * This method is responsible for finding all tickets by user ID and session state.
     * @param userId The user ID.
     * @param sessionState The session state.
     * @return List of tickets.
     */
    List<Ticket> findByUserIdAndSessionState(Long userId, SessionState sessionState);

    /**
     * This method is responsible for finding all tickets by license plate and session state.
     * @param licensePlate The license plate.
     * @param sessionState The session state.
     * @return List of tickets.
     */
    List<Ticket> findByLicensePlateAndSessionState(String licensePlate, SessionState sessionState);

    /**
     * This method is responsible for counting tickets by user ID and session state.
     * @param userId The user ID.
     * @param sessionState The session state.
     * @return Count of tickets.
     */
    long countByUserIdAndSessionState(Long userId, SessionState sessionState);

    /**
     * This method is responsible for finding all tickets by recognition unit ID ordered by creation date descending.
     * @param recognitionUnitId The recognition unit ID.
     * @return List of tickets.
     */
    List<Ticket> findByRecognitionUnitIdOrderByCreatedAtDesc(Long recognitionUnitId);
}
