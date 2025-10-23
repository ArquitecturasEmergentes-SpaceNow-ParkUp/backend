package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotBlank;

/**
 * ProcessQrCodeCommand
 * <p>
 *     This command represents the request to process a scanned QR code
 *     and identify the corresponding RecognitionUnit.
 * </p>
 */
public record ProcessQrCodeCommand(
    @NotBlank String fullCode
) {
    
    /**
     * Create a new ProcessQrCodeCommand
     * @param fullCode the full code from the scanned QR
     * @return new ProcessQrCodeCommand instance
     */
    public static ProcessQrCodeCommand of(String fullCode) {
        return new ProcessQrCodeCommand(fullCode);
    }
    
    /**
     * Extract server code from full code
     * @return the server code part
     */
    public String getServerCode() {
        if (this.fullCode == null || !this.fullCode.contains("-")) {
            return null;
        }
        return this.fullCode.substring(0, this.fullCode.lastIndexOf("-"));
    }
    
    /**
     * Extract unit ID from full code
     * @return the unit ID part
     */
    public String getUnitId() {
        if (this.fullCode == null || !this.fullCode.contains("-")) {
            return null;
        }
        return this.fullCode.substring(this.fullCode.lastIndexOf("-") + 1);
    }
}
