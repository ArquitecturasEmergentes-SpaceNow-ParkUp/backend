package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources.CreateReservationRequest;

/**
 * Assembler to convert CreateReservationRequest to CreateReservationCommand
 */
public class CreateReservationCommandFromResourceAssembler {

    /**
     * Converts a CreateReservationRequest to a CreateReservationCommand
     *
     * @param resource The CreateReservationRequest
     * @return The CreateReservationCommand
     */
    public static CreateReservationCommand toCommandFromResource(CreateReservationRequest resource) {
        return new CreateReservationCommand(
                resource.userId(),
                resource.parkingLotId(),
                resource.startTime(),
                resource.endTime()
        );
    }
}
