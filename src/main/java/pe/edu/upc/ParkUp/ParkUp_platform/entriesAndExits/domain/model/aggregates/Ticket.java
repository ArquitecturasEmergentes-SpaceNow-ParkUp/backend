package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.SessionState;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects.TicketType;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

/**
 * Ticket aggregate root
 * <p>
 *     This class represents the aggregate root for the Ticket entity.
 *     It manages the complete lifecycle of a vehicle's parking session.
 * </p>
 */
@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket extends AuditableAbstractAggregateRoot<Ticket> {

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Column(name = "license_plate", nullable = false, length = 20)
    private String licensePlate;

    @Column(name = "code", length = 50)
    private String code;

    @Embedded
    @Column(name = "type", nullable = false)
    private TicketType type;

    @Embedded
    @Column(name = "session_state", nullable = false)
    private SessionState sessionState;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recognition_unit_id")
    private RecognitionUnit recognitionUnit;

    public Ticket() {
        this.sessionState = SessionState.notPaid();
    }

    public Ticket(Long userId, String licensePlate, TicketType type, RecognitionUnit recognitionUnit) {
        this();
        this.userId = userId;
        this.licensePlate = licensePlate;
        this.type = type;
        this.recognitionUnit = recognitionUnit;
        this.entryTime = LocalDateTime.now();
    }

    /**
     * Process auto entry
     * @param recognitionUnit the recognition unit that processed the entry
     * @return the ticket with updated entry information
     */
    public Ticket processAutoEntry(RecognitionUnit recognitionUnit) {
        this.recognitionUnit = recognitionUnit;
        this.entryTime = LocalDateTime.now();
        this.sessionState = SessionState.notPaid();
        return this;
    }

    /**
     * Process auto exit
     * @return the ticket with updated exit information
     */
    public Ticket processAutoExit() {
        this.exitTime = LocalDateTime.now();
        this.sessionState = SessionState.paid();
        return this;
    }

    /**
     * Mark ticket as paid
     * @return the ticket with updated payment status
     */
    public Ticket markAsPaid() {
        this.sessionState = SessionState.paid();
        return this;
    }

    /**
     * Mark ticket as overdue
     * @return the ticket with updated overdue status
     */
    public Ticket markAsOverdue() {
        this.sessionState = SessionState.overdue();
        return this;
    }

    /**
     * Check if ticket is active
     * @return true if ticket is active, false otherwise
     */
    public boolean isActive() {
        return this.sessionState.isActive();
    }

    /**
     * Check if ticket is completed
     * @return true if ticket is completed, false otherwise
     */
    public boolean isCompleted() {
        return this.sessionState.isCompleted();
    }

    /**
     * Calculate parking duration in minutes
     * @return parking duration in minutes, or 0 if exit time is not set
     */
    public long calculateParkingDurationMinutes() {
        if (this.exitTime == null || this.entryTime == null) {
            return 0;
        }
        return java.time.Duration.between(this.entryTime, this.exitTime).toMinutes();
    }
}
