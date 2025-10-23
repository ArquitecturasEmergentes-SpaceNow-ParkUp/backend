package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;


import java.time.LocalDateTime;

/**
 * TicketResource
 * <p>
 *     This resource represents the ticket data transfer object.
 * </p>
 */
public record TicketResource(
    Long id,
    Long userId,
    String licensePlate,
    String code,
    String type,
    String sessionState,
    LocalDateTime entryTime,
    LocalDateTime exitTime,
    Long recognitionUnitId,
    String recognitionUnitIdentifier,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
    /**
     * Create a new TicketResource
     * @param id the ticket ID
     * @param userId the user ID
     * @param licensePlate the license plate
     * @param code the ticket code
     * @param type the ticket type
     * @param sessionState the session state
     * @param entryTime the entry time
     * @param exitTime the exit time
     * @param recognitionUnitId the recognition unit ID
     * @param recognitionUnitIdentifier the recognition unit identifier
     * @param createdAt the creation date
     * @param updatedAt the update date
     * @return new TicketResource instance
     */
    public static TicketResource of(Long id, Long userId, String licensePlate, String code, 
                                   String type, String sessionState, LocalDateTime entryTime, 
                                   LocalDateTime exitTime, Long recognitionUnitId, 
                                   String recognitionUnitIdentifier, LocalDateTime createdAt, 
                                   LocalDateTime updatedAt) {
        return new TicketResource(id, userId, licensePlate, code, type, sessionState, 
                                 entryTime, exitTime, recognitionUnitId, recognitionUnitIdentifier, 
                                 createdAt, updatedAt);
    }
}
