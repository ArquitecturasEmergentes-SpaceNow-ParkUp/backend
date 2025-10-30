package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources;

public record PaymentResource(Long id, Double amount, Long creditCardId, Long reservationId) {}
