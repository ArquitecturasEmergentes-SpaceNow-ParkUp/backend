package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * RegisterEdgeNodeCommand
 * <p>
 *     This command represents the request to register an Edge Node
 *     with its network configuration (IP and port).
 * </p>
 */
public record RegisterEdgeNodeCommand(
    @NotBlank String ipAddress,
    @NotNull Integer port,
    @NotBlank String identifier,
    @NotNull Long affiliateId,
    @NotNull Long parkingLotId
) {
    
    /**
     * Create a new RegisterEdgeNodeCommand
     * @param ipAddress the IP address of the Edge Node
     * @param port the port of the Edge Node
     * @param identifier the unique identifier
     * @param affiliateId the affiliate ID
     * @param parkingLotId the parking lot ID
     * @return new RegisterEdgeNodeCommand instance
     */
    public static RegisterEdgeNodeCommand of(String ipAddress, Integer port, String identifier, 
                                           Long affiliateId, Long parkingLotId) {
        return new RegisterEdgeNodeCommand(ipAddress, port, identifier, affiliateId, parkingLotId);
    }
}
