package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources;


public record CreatePaymentResource(Long creditCardId, Double amount) {

    public CreatePaymentResource {
        if (creditCardId == null || creditCardId <= 0) {
            throw new IllegalArgumentException("Credit card ID must be a positive number.");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number.");
        }
    }
}
