package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands;

public record AddParkingLotMapCommand(
        Long parkingLotId,
        String layoutJson,
        int totalSpaces,
        int disabilitySpaces
) {}