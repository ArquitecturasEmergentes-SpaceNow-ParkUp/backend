package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources;

public record CreditCardResource(Long creditCardId,String cardNumber, String cardHolderName, String expirationDate, String cvv,  Long userId)  {
}
