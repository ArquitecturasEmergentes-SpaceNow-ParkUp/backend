package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EdgeNodeResource(
        @NotBlank String ipAddress,
        @NotNull Integer port,
        @NotBlank String identifier,
        @NotNull Long affiliateId,
        @NotNull Long parkingLotId
) { }