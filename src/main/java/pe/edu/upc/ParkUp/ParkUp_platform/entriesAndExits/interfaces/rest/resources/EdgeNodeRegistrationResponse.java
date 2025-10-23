package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

/**
 * EdgeNodeRegistrationResponse
 * <p>
 *     This resource represents the edge node registration response data transfer object.
 * </p>
 */
public record EdgeNodeRegistrationResponse(
    String code,
    Long recognitionUnitId,
    String message
) {
    
    /**
     * Create a new EdgeNodeRegistrationResponse
     * @param code the server assigned code
     * @param recognitionUnitId the recognition unit ID
     * @param message the response message
     * @return new EdgeNodeRegistrationResponse instance
     */
    public static EdgeNodeRegistrationResponse of(String code, Long recognitionUnitId, String message) {
        return new EdgeNodeRegistrationResponse(code, recognitionUnitId, message);
    }
}
