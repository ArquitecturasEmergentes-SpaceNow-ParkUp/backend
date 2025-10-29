package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands;

public record CreatePaymentCommand(Long creditCardId, Double amount){}


