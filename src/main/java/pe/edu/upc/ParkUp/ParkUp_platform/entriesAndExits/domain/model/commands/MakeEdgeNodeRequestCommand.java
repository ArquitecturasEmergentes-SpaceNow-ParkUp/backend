package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import org.springframework.http.HttpMethod;

/**
 * MakeEdgeNodeRequestCommand
 * <p>
 *     This command represents the request to make an HTTP call to an Edge Node.
 * </p>
 */
public record MakeEdgeNodeRequestCommand(
    Long recognitionUnitId,
    String endpoint,
    HttpMethod method,
    String requestBody
) {
    
    /**
     * Create a new MakeEdgeNodeRequestCommand
     * @param recognitionUnitId the recognition unit ID
     * @param endpoint the endpoint to call on the Edge Node
     * @param method the HTTP method
     * @param requestBody the request body (optional)
     * @return new MakeEdgeNodeRequestCommand instance
     */
    public static MakeEdgeNodeRequestCommand of(Long recognitionUnitId, String endpoint, 
                                               HttpMethod method, String requestBody) {
        return new MakeEdgeNodeRequestCommand(recognitionUnitId, endpoint, method, requestBody);
    }
}
