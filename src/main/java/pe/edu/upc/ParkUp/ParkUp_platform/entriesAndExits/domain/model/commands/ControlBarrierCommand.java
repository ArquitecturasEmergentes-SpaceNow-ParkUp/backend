package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotNull;

/**
 * ControlBarrierCommand
 * <p>
 *     This command represents the request to control a barrier
 *     (open or close) at a recognition unit.
 * </p>
 */
public record ControlBarrierCommand(
    @NotNull Long recognitionUnitId,
    @NotNull BarrierAction action
) {
    
    /**
     * Create a new ControlBarrierCommand
     * @param recognitionUnitId the ID of the recognition unit
     * @param action the barrier action to perform
     * @return new ControlBarrierCommand instance
     */
    public static ControlBarrierCommand of(Long recognitionUnitId, BarrierAction action) {
        return new ControlBarrierCommand(recognitionUnitId, action);
    }
    
    /**
     * Barrier action enumeration
     */
    public enum BarrierAction {
        OPEN, CLOSE
    }
}
