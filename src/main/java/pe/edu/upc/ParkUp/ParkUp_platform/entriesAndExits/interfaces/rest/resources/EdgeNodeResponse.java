package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

/**
 * EdgeNodeResponse
 * <p>
 *     This resource represents the response sent back to the Python edge node service.
 *     It matches the response structure expected by the edge node.
 * </p>
 */
public record EdgeNodeResponse(
    Long id
) {
    
    /**
     * Create a new EdgeNodeResponse
     * @param id the ID of the registered/updated edge node
     * @return new EdgeNodeResponse instance
     */
    public static EdgeNodeResponse of(Long id) {
        return new EdgeNodeResponse(id);
    }
}
