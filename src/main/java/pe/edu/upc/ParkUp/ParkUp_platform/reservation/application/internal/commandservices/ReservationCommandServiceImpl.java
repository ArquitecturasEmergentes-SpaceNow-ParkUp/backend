package pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.outboundservices.NotificationService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.*;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;

import java.util.Optional;

/**
 * Implementation of ReservationCommandService
 * Handles all write operations for reservations with transaction management
 */
@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository,
                                        NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(CreateReservationCommand command) {
        // Validate availability - check for conflicting reservations
        boolean hasConflict = reservationRepository.existsConflictingReservation(
                command.parkingLotId(),
                command.startTime(),
                command.endTime()
        );

        if (hasConflict) {
            throw new IllegalStateException("Parking slot is not available for the selected time range");
        }

        // Create new reservation
        var reservation = new Reservation(
                command.userId(),
                command.parkingLotId(),
                command.startTime(),
                command.endTime()
        );

        // Save reservation
        var savedReservation = reservationRepository.save(reservation);

        // TODO: Publish domain events using Spring ApplicationEventPublisher
        // - ReservationCreatedEvent
        // - PaymentRequestedEvent (for Payments BC)

        return Optional.of(savedReservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(CancelReservationCommand command) {
        var reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Validate cancellation policy (e.g., 1 hour before start time)
        if (!reservation.canBeCancelled(1)) {
            throw new IllegalStateException("Reservation cannot be cancelled. Cancellation policy violation.");
        }

        // Cancel reservation
        reservation.cancel();
        var savedReservation = reservationRepository.save(reservation);

        // Send cancellation notification
        notificationService.sendReservationCancelledNotification(
                savedReservation.getUserId(),
                savedReservation.getId()
        );

        return Optional.of(savedReservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(ConfirmPaymentCommand command) {
        var reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Confirm payment
        reservation.confirmPayment();
        var savedReservation = reservationRepository.save(reservation);

        // Send WhatsApp notification (don't fail if notification fails)
        try {
            notificationService.sendReservationConfirmedNotification(
                    savedReservation.getUserId(), 
                    savedReservation.getId()
            );
        } catch (Exception e) {
            System.err.println("Failed to send notification, but reservation was confirmed: " + e.getMessage());
        }

        return Optional.of(savedReservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(StartReservationCommand command) {
        // Find active reservation for user
        var reservation = reservationRepository.findActiveReservationByUserId(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("No active reservation found for user"));

        // Validate reservation ID matches
        if (!reservation.getId().equals(command.reservationId())) {
            throw new IllegalArgumentException("Reservation ID mismatch");
        }

        // Start reservation
        reservation.start();
        var savedReservation = reservationRepository.save(reservation);

        // TODO: Publish domain event using Spring ApplicationEventPublisher
        // - ReservationStartedEvent (for Notifications BC)

        return Optional.of(savedReservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(CompleteReservationCommand command) {
        var reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Validate user owns the reservation
        if (!reservation.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("User does not own this reservation");
        }

        // Complete reservation
        reservation.complete();
        var savedReservation = reservationRepository.save(reservation);

        // TODO: Publish domain event using Spring ApplicationEventPublisher
        // - ReservationCompletedEvent (for Payments BC - final charge)

        return Optional.of(savedReservation);
    }
}
