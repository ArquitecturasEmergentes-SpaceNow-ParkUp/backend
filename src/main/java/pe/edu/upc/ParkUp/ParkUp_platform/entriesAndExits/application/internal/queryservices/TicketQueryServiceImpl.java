package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetActiveTicketByLicensePlateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetTicketByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.SessionState;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.TicketQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * TicketQueryServiceImpl
 * <p>
 *     This class implements the TicketQueryService interface and provides
 *     the implementation for ticket query operations.
 * </p>
 */
@Service
public class TicketQueryServiceImpl implements TicketQueryService {

    private final TicketRepository ticketRepository;

    public TicketQueryServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> handle(GetTicketByIdQuery query) {
        return ticketRepository.findById(query.ticketId());
    }

    @Override
    public Optional<Ticket> handle(GetActiveTicketByLicensePlateQuery query) {
        List<Ticket> activeTickets = ticketRepository.findByLicensePlateAndSessionState(
            query.licensePlate(), 
            SessionState.notPaid()
        );
        return activeTickets.isEmpty() ? Optional.empty() : Optional.of(activeTickets.get(0));
    }

    @Override
    public List<Ticket> getAllTicketsByUserId(Long userId) {
        return ticketRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
