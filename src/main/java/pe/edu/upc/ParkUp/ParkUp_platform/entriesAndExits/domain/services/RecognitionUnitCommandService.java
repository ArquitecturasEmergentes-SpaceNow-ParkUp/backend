package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ControlBarrierCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.CreateRecognitionUnitCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessQrCodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.GenerateQrCodeCommand;

import java.util.Optional;

/**
 * RecognitionUnitCommandService
 * <p>
 *     This service defines the command operations for RecognitionUnit entity.
 * </p>
 */
public interface RecognitionUnitCommandService {
    
    /**
     * Create recognition unit
     * @param command the create recognition unit command
     * @return the created recognition unit
     */
    RecognitionUnit handle(CreateRecognitionUnitCommand command);
    
    /**
     * Control barrier
     * @param command the control barrier command
     * @return optional recognition unit if successful
     */
    Optional<RecognitionUnit> handle(ControlBarrierCommand command);
    
    /**
     * Register edge node
     * @param command the register edge node command
     * @return the registered recognition unit
     */
    RecognitionUnit handle(RegisterEdgeNodeCommand command);
    
    /**
     * Process QR code
     * @param command the process QR code command
     * @return optional recognition unit if found
     */
    Optional<RecognitionUnit> handle(ProcessQrCodeCommand command);
    
    /**
     * Generate QR code
     * @param command the generate QR code command
     * @return optional recognition unit with QR code
     */
    Optional<RecognitionUnit> handle(GenerateQrCodeCommand command);
}
