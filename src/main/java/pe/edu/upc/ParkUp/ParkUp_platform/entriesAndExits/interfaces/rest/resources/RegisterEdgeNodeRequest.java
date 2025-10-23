package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * RegisterEdgeNodeRequest
 * <p>
 *     This resource represents the register edge node request data transfer object.
 * </p>
 */
public record RegisterEdgeNodeRequest(
    @NotBlank String ipAddress,
    @NotNull Integer port,
    @NotBlank String identifier,
    @NotNull Long affiliateId,
    @NotNull Long parkingLotId
) {
    
    /**
     * Create a new RegisterEdgeNodeRequest
     * @param ipAddress the IP address
     * @param port the port
     * @param identifier the identifier
     * @param affiliateId the affiliate ID
     * @param parkingLotId the parking lot ID
     * @return new RegisterEdgeNodeRequest instance
     */
    public static RegisterEdgeNodeRequest of(String ipAddress, Integer port, String identifier, 
                                           Long affiliateId, Long parkingLotId) {
        return new RegisterEdgeNodeRequest(ipAddress, port, identifier, affiliateId, parkingLotId);
    }
}
