package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO para la solicitud POST (el JSON que recibe tu API)
public record CreateExtraordinaryCaseResource(
        @NotNull Long parkingLotId,
        @NotNull Long recognitionUnitId,
        @NotBlank String plateNumber,
        String caseReason
) {
}