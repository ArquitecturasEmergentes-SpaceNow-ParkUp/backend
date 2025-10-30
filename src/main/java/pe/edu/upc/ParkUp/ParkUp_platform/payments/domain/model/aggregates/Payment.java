package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_card_id", nullable = false)
    private CreditCard creditCard;
    
    @Column(name = "reservation_id")
    private Long reservationId;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    public Payment(Double amount, CreditCard creditCard, Long reservationId) {
        this.amount = amount;
        this.creditCard = creditCard;
        this.reservationId = reservationId;
        this.createdAt = java.time.LocalDateTime.now();
    }


    public Payment() {

    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getCreditCardId() {
        return creditCard.getId();
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return creditCard.getUserId();
    }
}
