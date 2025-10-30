package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.UserProfileResource;

/**
 * Assembler to transform UserProfile entity to UserProfileResource
 */
public class UserProfileResourceFromEntityAssembler {

    /**
     * Transforms a UserProfile entity to a UserProfileResource
     *
     * @param entity The entity to transform
     * @return The resource
     */
    public static UserProfileResource toResourceFromEntity(UserProfile entity) {
        return new UserProfileResource(
                entity.getId(),
                entity.getUserId(),
                entity.getFullName().getFirstName(),
                entity.getFullName().getLastName(),
                entity.getFullName().getFullName(),
                entity.getDocumentId().getDocumentNumber(),
                entity.getPhoneNumber().getCountryCode(),
                entity.getPhoneNumber().getNumber(),
                entity.getPhoneNumber().getFullPhoneNumber(),
                entity.getProfileImageUrl(),
                entity.getNotificationsEnabled(),
                entity.getEmailNotificationsEnabled(),
                entity.getSmsNotificationsEnabled()
        );
    }
}
