package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TicketType value object
 * <p>
 *     This value object represents the type of ticket (QR or PHYSICAL).
 * </p>
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TicketType {

    @Enumerated(EnumType.STRING)
    private Type type;

    public TicketType(Type type) {
        this.type = type;
    }

    public static TicketType qr() {
        return new TicketType(Type.QR);
    }

    public static TicketType physical() {
        return new TicketType(Type.PHYSICAL);
    }

    public boolean isQr() {
        return this.type == Type.QR;
    }

    public boolean isPhysical() {
        return this.type == Type.PHYSICAL;
    }

    public String name() {
        return this.type != null ? this.type.name() : null;
    }

    public enum Type {
        QR, PHYSICAL
    }
}
