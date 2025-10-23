package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

/**
 * BarrierControlRequest
 * <p>
 *     This resource represents the barrier control request data transfer object.
 * </p>
 */
public record BarrierControlRequest(
    @NotNull Long recognitionUnitId,
    @NotNull String action
) {
    
    /**
     * Create a new BarrierControlRequest
     * @param recognitionUnitId the recognition unit ID
     * @param action the barrier action
     * @return new BarrierControlRequest instance
     */
    public static BarrierControlRequest of(Long recognitionUnitId, String action) {
        return new BarrierControlRequest(recognitionUnitId, action);
    }
}
