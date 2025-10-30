package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.CreateUserProfileCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.CreateUserProfileResource;

/**
 * Assembler to transform CreateUserProfileResource to CreateUserProfileCommand
 */
public class CreateUserProfileCommandFromResourceAssembler {

    /**
     * Transforms a CreateUserProfileResource to a CreateUserProfileCommand
     *
     * @param resource The resource to transform
     * @return The command
     */
    public static CreateUserProfileCommand toCommandFromResource(CreateUserProfileResource resource) {
        return new CreateUserProfileCommand(
                resource.userId(),
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.countryCode(),
                resource.phoneNumber()
        );
    }
}
