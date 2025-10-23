package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

/**
 * QrCodeResponse
 * <p>
 *     This resource represents the QR code response data transfer object.
 * </p>
 */
public record QrCodeResponse(
    String fullCode,
    String qrCode,
    Long recognitionUnitId,
    String serverCode
) {
    
    /**
     * Create a new QrCodeResponse
     * @param fullCode the full code
     * @param qrCode the QR code string
     * @param recognitionUnitId the recognition unit ID
     * @param serverCode the server code
     * @return new QrCodeResponse instance
     */
    public static QrCodeResponse of(String fullCode, String qrCode, Long recognitionUnitId, String serverCode) {
        return new QrCodeResponse(fullCode, qrCode, recognitionUnitId, serverCode);
    }
}
