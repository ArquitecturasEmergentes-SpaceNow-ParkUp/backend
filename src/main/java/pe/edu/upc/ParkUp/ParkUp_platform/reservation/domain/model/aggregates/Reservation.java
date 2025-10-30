package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects.ParkingSlotId;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects.ReservationStatus;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

/**
 * Reservation aggregate root
 * Represents a parking slot reservation made by a user
 *
 * @see AuditableAbstractAggregateRoot
 */
@Entity
@Table(name = "reservations")
@Getter
public class Reservation extends AuditableAbstractAggregateRoot<Reservation> {

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @Embedded
    @NotNull
    private ParkingSlotId parkingSlotId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column
    private LocalDateTime actualStartTime;

    @Column
    private LocalDateTime actualEndTime;

    protected Reservation() {
    }

    /**
     * Constructor for creating a new reservation
     *
     * @param userId         The ID of the user making the reservation
     * @param parkingLotId   The ID of the parking lot
     * @param startTime      The planned start time of the reservation
     * @param endTime        The planned end time of the reservation
     */
    public Reservation(Long userId, Long parkingLotId, LocalDateTime startTime, LocalDateTime endTime) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past");
        }

        this.userId = userId;
        this.parkingSlotId = new ParkingSlotId(parkingLotId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.PENDING_PAYMENT;
    }

    /**
     * Confirms the reservation after successful payment
     */
    public void confirmPayment() {
        if (this.status != ReservationStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Can only confirm payment for reservations with PENDING_PAYMENT status");
        }
        this.status = ReservationStatus.ACTIVE;
    }

    /**
     * Cancels the reservation
     */
    public void cancel() {
        if (this.status == ReservationStatus.COMPLETED || this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel a completed or already cancelled reservation");
        }
        if (this.status == ReservationStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot cancel a reservation that is in progress");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    /**
     * Starts the reservation when user arrives
     */
    public void start() {
        if (this.status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Can only start reservations with ACTIVE status");
        }
        this.actualStartTime = LocalDateTime.now();
        this.status = ReservationStatus.IN_PROGRESS;
    }

    /**
     * Completes the reservation when user departs
     */
    public void complete() {
        if (this.status != ReservationStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only complete reservations with IN_PROGRESS status");
        }
        this.actualEndTime = LocalDateTime.now();
        this.status = ReservationStatus.COMPLETED;
    }

    /**
     * Marks the reservation as expired
     */
    public void expire() {
        if (this.status == ReservationStatus.COMPLETED || this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Cannot expire a completed or cancelled reservation");
        }
        this.status = ReservationStatus.EXPIRED;
    }

    /**
     * Checks if the reservation can be cancelled based on cancellation policy
     *
     * @param hoursBeforeStart Minimum hours before start time required for cancellation
     * @return true if cancellation is allowed
     */
    public boolean canBeCancelled(int hoursBeforeStart) {
        if (this.status != ReservationStatus.ACTIVE && this.status != ReservationStatus.PENDING_PAYMENT) {
            return false;
        }
        LocalDateTime cancellationDeadline = this.startTime.minusHours(hoursBeforeStart);
        return LocalDateTime.now().isBefore(cancellationDeadline);
    }

    /**
     * Checks if the reservation has expired
     *
     * @return true if the reservation end time has passed and status is IN_PROGRESS
     */
    public boolean hasExpired() {
        return this.status == ReservationStatus.IN_PROGRESS && LocalDateTime.now().isAfter(this.endTime);
    }

    /**
     * Checks if the reservation should send a reminder notification
     *
     * @param minutesBefore Minutes before start time to send reminder
     * @return true if reminder should be sent
     */
    public boolean shouldSendReminder(int minutesBefore) {
        if (this.status != ReservationStatus.ACTIVE) {
            return false;
        }
        LocalDateTime reminderTime = this.startTime.minusMinutes(minutesBefore);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(reminderTime) && now.isBefore(this.startTime);
    }
}
