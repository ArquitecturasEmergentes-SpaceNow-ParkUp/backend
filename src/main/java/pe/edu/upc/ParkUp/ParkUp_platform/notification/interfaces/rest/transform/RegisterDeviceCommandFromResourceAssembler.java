package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.RegisterDeviceCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.RegisterDeviceRequest;

/**
 * Assembler to convert RegisterDeviceRequest to RegisterDeviceCommand
 */
public class RegisterDeviceCommandFromResourceAssembler {

    /**
     * Converts a RegisterDeviceRequest to a RegisterDeviceCommand
     *
     * @param resource The RegisterDeviceRequest
     * @return The RegisterDeviceCommand
     */
    public static RegisterDeviceCommand toCommandFromResource(RegisterDeviceRequest resource) {
        return new RegisterDeviceCommand(
                resource.userId(),
                resource.deviceToken(),
                resource.deviceType(),
                resource.deviceName()
        );
    }
}
