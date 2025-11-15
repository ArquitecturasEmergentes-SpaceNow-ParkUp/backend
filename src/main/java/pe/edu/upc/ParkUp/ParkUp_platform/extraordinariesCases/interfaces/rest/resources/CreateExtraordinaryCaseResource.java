package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources;



// DTO para la solicitud POST (el JSON que recibe tu API)
public record CreateExtraordinaryCaseResource(
        Long parkingLotId,
        Long recognitionUnitId,
        String plateNumber,
        String caseReason
) {
}