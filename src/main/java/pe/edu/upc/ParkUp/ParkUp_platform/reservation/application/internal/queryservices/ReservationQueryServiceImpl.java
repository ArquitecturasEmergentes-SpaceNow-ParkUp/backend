package pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.*;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ReservationQueryService
 * Handles all read operations for reservations
 */
@Service
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public ReservationQueryServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Optional<Reservation> handle(GetReservationByIdQuery query) {
        return reservationRepository.findById(query.reservationId());
    }

    @Override
    public List<Reservation> handle(GetReservationsByUserIdQuery query) {
        return reservationRepository.findByUserId(query.userId());
    }

    @Override
    public List<Reservation> handle(GetReservationsByStatusQuery query) {
        return reservationRepository.findByStatus(query.status());
    }

    @Override
    public Optional<Reservation> handle(GetActiveReservationByUserIdQuery query) {
        return reservationRepository.findActiveReservationByUserId(query.userId());
    }

    @Override
    public List<Reservation> handle(GetReservationsNeedingRemindersQuery query) {
        return reservationRepository.findActiveReservationsStartingBetween(
                query.afterTime(),
                query.beforeTime()
        );
    }
}
