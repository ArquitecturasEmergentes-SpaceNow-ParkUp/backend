package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetActiveTicketByLicensePlateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetTicketByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * TicketQueryService
 * <p>
 *     This service defines the query operations for Ticket aggregate.
 * </p>
 */
public interface TicketQueryService {
    
    /**
     * Get ticket by ID
     * @param query the get ticket by ID query
     * @return optional ticket if found
     */
    Optional<Ticket> handle(GetTicketByIdQuery query);
    
    /**
     * Get active ticket by license plate
     * @param query the get active ticket by license plate query
     * @return optional ticket if found
     */
    Optional<Ticket> handle(GetActiveTicketByLicensePlateQuery query);
    
    /**
     * Get all tickets by user ID
     * @param userId the user ID
     * @return list of tickets
     */
    List<Ticket> getAllTicketsByUserId(Long userId);
}
