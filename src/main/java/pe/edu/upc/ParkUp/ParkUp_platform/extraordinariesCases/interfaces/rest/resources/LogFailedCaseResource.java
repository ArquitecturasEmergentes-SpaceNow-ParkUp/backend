package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record LogFailedCaseResource(
        @NotBlank(message = "Failure reason cannot be blank")
        String failureReason
) {
}