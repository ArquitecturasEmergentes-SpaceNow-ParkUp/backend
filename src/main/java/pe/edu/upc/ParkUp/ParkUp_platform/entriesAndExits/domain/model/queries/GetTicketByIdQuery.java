package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries;

import jakarta.validation.constraints.NotNull;

/**
 * GetTicketByIdQuery
 * <p>
 *     This query represents the request to get a ticket by its ID.
 * </p>
 */
public record GetTicketByIdQuery(
    @NotNull Long ticketId
) {
    
    /**
     * Create a new GetTicketByIdQuery
     * @param ticketId the ID of the ticket
     * @return new GetTicketByIdQuery instance
     */
    public static GetTicketByIdQuery of(Long ticketId) {
        return new GetTicketByIdQuery(ticketId);
    }
}
