package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * ProcessQrCodeRequest
 * <p>
 *     This resource represents the process QR code request data transfer object.
 * </p>
 */
public record ProcessQrCodeRequest(
    @NotBlank String fullCode
) {
    
    /**
     * Create a new ProcessQrCodeRequest
     * @param fullCode the full code from scanned QR
     * @return new ProcessQrCodeRequest instance
     */
    public static ProcessQrCodeRequest of(String fullCode) {
        return new ProcessQrCodeRequest(fullCode);
    }
}
