package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources;
//
public record ExtraordinaryCaseResource(
        Long id,
        Long parkingLotId,
        Long recognitionUnitId,
        String plateNumber,
        String caseReason,
        String status,
        String createdAt
) {
}