package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoEntryCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoExitCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.SessionState;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.TicketType;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.TicketCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * TicketCommandServiceImpl
 * <p>
 *     This class implements the TicketCommandService interface and provides
 *     the implementation for ticket command operations.
 * </p>
 */
@Service
@Transactional
public class TicketCommandServiceImpl implements TicketCommandService {

    private final TicketRepository ticketRepository;
    private final RecognitionUnitRepository recognitionUnitRepository;

    public TicketCommandServiceImpl(TicketRepository ticketRepository, 
                                   RecognitionUnitRepository recognitionUnitRepository) {
        this.ticketRepository = ticketRepository;
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @Override
    public Optional<Ticket> handle(ProcessAutoEntryCommand command) {

        List<Ticket> activeTickets = ticketRepository.findByLicensePlateAndSessionState(
            command.licensePlate(), 
            SessionState.notPaid()
        );
        Optional<Ticket> existingTicket = activeTickets.isEmpty() ? Optional.empty() : Optional.of(activeTickets.get(0));
        if (existingTicket.isPresent()) {
            throw new RuntimeException("Vehicle with license plate " + command.licensePlate() + " is already inside the parking lot");
        }

        RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(command.recognitionUnitId())
                .orElseThrow(() -> new RuntimeException("Recognition unit not found with ID: " + command.recognitionUnitId()));

        if (!recognitionUnit.isActive()) {
            throw new RuntimeException("Recognition unit is not active");
        }

        if (!recognitionUnit.isAtEntrance()) {
            throw new RuntimeException("Recognition unit is not at entrance location");
        }

        Ticket ticket = new Ticket(command.userId(), command.licensePlate(), TicketType.physical(), recognitionUnit);
        ticket.processAutoEntry(recognitionUnit);

        Ticket savedTicket = ticketRepository.save(ticket);
        return Optional.of(savedTicket);
    }

    @Override
    public Optional<Ticket> handle(ProcessAutoExitCommand command) {

        List<Ticket> activeTickets = ticketRepository.findByLicensePlateAndSessionState(
            command.licensePlate(), 
            SessionState.notPaid()
        );
        Optional<Ticket> ticketOptional = activeTickets.isEmpty() ? Optional.empty() : Optional.of(activeTickets.get(0));
        if (ticketOptional.isEmpty()) {
            throw new RuntimeException("No active ticket found for license plate: " + command.licensePlate());
        }

        Ticket ticket = ticketOptional.get();

        RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(command.recognitionUnitId())
                .orElseThrow(() -> new RuntimeException("Recognition unit not found with ID: " + command.recognitionUnitId()));

        if (!recognitionUnit.isActive()) {
            throw new RuntimeException("Recognition unit is not active");
        }

        if (!recognitionUnit.isAtExit()) {
            throw new RuntimeException("Recognition unit is not at exit location");
        }

        ticket.processAutoExit();

        Ticket savedTicket = ticketRepository.save(ticket);
        return Optional.of(savedTicket);
    }
}
