package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands;


public record CreateCreditCardCommand (String cardNumber, String cardHolderName, String expirationDate, String cvv, Long userId) {
    public CreateCreditCardCommand {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Card number cannot be null or blank");
        }
        if (cardHolderName == null || cardHolderName.isBlank()) {
            throw new IllegalArgumentException("Card holder name cannot be null or blank");
        }
        if (expirationDate == null || expirationDate.isBlank()) {
            throw new IllegalArgumentException("Expiration date cannot be null or blank");
        }
        if (cvv == null || cvv.isBlank()) {
            throw new IllegalArgumentException("CVV cannot be null or blank");
        }
    }
}
