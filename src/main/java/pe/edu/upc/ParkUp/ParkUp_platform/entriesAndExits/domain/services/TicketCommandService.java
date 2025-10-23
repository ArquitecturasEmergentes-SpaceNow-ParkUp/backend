package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoEntryCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoExitCommand;

import java.util.Optional;

/**
 * TicketCommandService
 * <p>
 *     This service defines the command operations for Ticket aggregate.
 * </p>
 */
public interface TicketCommandService {
    
    /**
     * Process auto entry command
     * @param command the auto entry command
     * @return optional ticket if successful
     */
    Optional<Ticket> handle(ProcessAutoEntryCommand command);
    
    /**
     * Process auto exit command
     * @param command the auto exit command
     * @return optional ticket if successful
     */
    Optional<Ticket> handle(ProcessAutoExitCommand command);
}
