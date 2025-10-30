package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.UpdateUserProfileCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.UpdateUserProfileResource;

/**
 * Assembler to transform UpdateUserProfileResource to UpdateUserProfileCommand
 */
public class UpdateUserProfileCommandFromResourceAssembler {

    /**
     * Transforms an UpdateUserProfileResource to an UpdateUserProfileCommand
     *
     * @param profileId The profile ID
     * @param resource  The resource to transform
     * @return The command
     */
    public static UpdateUserProfileCommand toCommandFromResource(Long profileId, UpdateUserProfileResource resource) {
        return new UpdateUserProfileCommand(
                profileId,
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.countryCode(),
                resource.phoneNumber()
        );
    }
}
