package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.aggregates.User;


@Entity
public class CreditCard {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank
    @Size(max = 12)
    @Column(name = "card_number")
    private String cardNumber;

    @Getter
    @NotBlank
    @Size(max = 50)
    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Getter
    @NotBlank
    @Size(max = 5)
    @Column(name = "expiration_date")
    private String expirationDate;

    @Getter
    @NotBlank
    @Size(max = 3)
    @Column(name = "cvv")
    private String cvv;

    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CreditCard() {}

    public CreditCard(String cardNumber, String cardHolderName, String expirationDate, String cvv, User user) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }

    // Optional setters if needed for update cases:
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getCardHolderName() {
        return cardHolderName;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public String getCvv() {
        return cvv;
    }
}

