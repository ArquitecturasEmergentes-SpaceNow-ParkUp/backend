package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotNull;

/**
 * GenerateQrCodeCommand
 * <p>
 *     This command represents the request to generate a QR code
 *     for a specific RecognitionUnit.
 * </p>
 */
public record GenerateQrCodeCommand(
    @NotNull Long recognitionUnitId
) {
    
    /**
     * Create a new GenerateQrCodeCommand
     * @param recognitionUnitId the ID of the recognition unit
     * @return new GenerateQrCodeCommand instance
     */
    public static GenerateQrCodeCommand of(Long recognitionUnitId) {
        return new GenerateQrCodeCommand(recognitionUnitId);
    }
}
