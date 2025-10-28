package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * EdgeNodeRegistrationRequest
 * <p>
 *     This resource represents the edge node registration request from the Python edge node service.
 *     It matches the payload structure expected by the edge node.
 * </p>
 */
public record EdgeNodeRegistrationRequest(
    @NotBlank String url,
    @NotNull Integer port
) {
    
    /**
     * Create a new EdgeNodeRegistrationRequest
     * @param url the URL/IP address of the edge node
     * @param port the port of the edge node
     * @return new EdgeNodeRegistrationRequest instance
     */
    public static EdgeNodeRegistrationRequest of(String url, Integer port) {
        return new EdgeNodeRegistrationRequest(url, port);
    }
}
