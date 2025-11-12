package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands;

public record CreateExtraordinaryCaseCommand(
        Long parkingLotId,
        Long recognitionUnitId,
        String plateNumber,
        String caseReason
) {
}