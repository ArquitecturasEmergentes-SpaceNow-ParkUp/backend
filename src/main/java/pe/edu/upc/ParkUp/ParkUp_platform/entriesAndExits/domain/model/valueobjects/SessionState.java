package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SessionState value object
 * <p>
 *     This value object represents the state of a parking session.
 * </p>
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SessionState {

    @Enumerated(EnumType.STRING)
    private State state;

    public SessionState(State state) {
        this.state = state;
    }

    public static SessionState notPaid() {
        return new SessionState(State.NOT_PAID);
    }

    public static SessionState autoPay() {
        return new SessionState(State.AUTO_PAY);
    }

    public static SessionState paid() {
        return new SessionState(State.PAID);
    }

    public static SessionState overdue() {
        return new SessionState(State.OVERDUE);
    }

    public boolean isNotPaid() {
        return this.state == State.NOT_PAID;
    }

    public boolean isAutoPay() {
        return this.state == State.AUTO_PAY;
    }

    public boolean isPaid() {
        return this.state == State.PAID;
    }

    public boolean isOverdue() {
        return this.state == State.OVERDUE;
    }

    public boolean isActive() {
        return this.state == State.NOT_PAID || this.state == State.AUTO_PAY;
    }

    public boolean isCompleted() {
        return this.state == State.PAID;
    }

    public String name() {
        return this.state != null ? this.state.name() : null;
    }

    public enum State {
        NOT_PAID, AUTO_PAY, PAID, OVERDUE
    }
}
