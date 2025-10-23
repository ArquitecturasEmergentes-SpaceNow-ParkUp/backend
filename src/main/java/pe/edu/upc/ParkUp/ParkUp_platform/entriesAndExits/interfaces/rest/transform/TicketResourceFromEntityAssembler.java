package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.TicketResource;

/**
 * TicketResourceFromEntityAssembler
 * <p>
 *     This class is responsible for transforming Ticket entities to TicketResource DTOs.
 * </p>
 */
public class TicketResourceFromEntityAssembler {

    /**
     * Transform Ticket entity to TicketResource
     * @param ticket the ticket entity
     * @return the ticket resource
     */
    public static TicketResource toResourceFromEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return TicketResource.of(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getLicensePlate(),
                ticket.getCode(),
                ticket.getType() != null ? ticket.getType().name() : null,
                ticket.getSessionState() != null ? ticket.getSessionState().name() : null,
                ticket.getEntryTime(),
                ticket.getExitTime(),
                ticket.getRecognitionUnit() != null ? ticket.getRecognitionUnit().getId() : null,
                ticket.getRecognitionUnit() != null ? ticket.getRecognitionUnit().getIdentifier() : null,
                ticket.getCreatedAt() != null ? ticket.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                ticket.getUpdatedAt() != null ? ticket.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}
