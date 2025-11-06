package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands;

public record EditParkingLotMapCommand(
        Long parkingLotId,
        Long mapId,
        String newLayoutJson,
        int newTotalSpaces,
        int newDisabilitySpaces
) {}