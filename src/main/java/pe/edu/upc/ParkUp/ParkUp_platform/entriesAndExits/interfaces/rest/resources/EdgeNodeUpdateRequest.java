package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * EdgeNodeUpdateRequest
 * <p>
 *     This resource represents the edge node update request from the Python edge node service.
 *     It matches the payload structure expected by the edge node for updates.
 * </p>
 */
public record EdgeNodeUpdateRequest(
    @NotNull Long id,
    @NotBlank String url,
    @NotNull Integer port
) {
    
    /**
     * Create a new EdgeNodeUpdateRequest
     * @param id the ID of the edge node
     * @param url the URL/IP address of the edge node
     * @param port the port of the edge node
     * @return new EdgeNodeUpdateRequest instance
     */
    public static EdgeNodeUpdateRequest of(Long id, String url, Integer port) {
        return new EdgeNodeUpdateRequest(id, url, port);
    }
}
