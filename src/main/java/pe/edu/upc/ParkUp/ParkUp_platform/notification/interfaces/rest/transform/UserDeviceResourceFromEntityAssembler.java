package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.UserDeviceResource;

/**
 * Assembler to convert UserDevice entity to UserDeviceResource
 */
public class UserDeviceResourceFromEntityAssembler {

    /**
     * Converts a UserDevice entity to a UserDeviceResource
     * Masks the device token for security
     *
     * @param entity The UserDevice entity
     * @return The UserDeviceResource
     */
    public static UserDeviceResource toResourceFromEntity(UserDevice entity) {
        // Mask device token for security (show only last 8 characters)
        String maskedToken = maskDeviceToken(entity.getDeviceToken());
        
        return new UserDeviceResource(
                entity.getId(),
                entity.getUserId(),
                maskedToken,
                entity.getDeviceType(),
                entity.getDeviceName(),
                entity.getIsActive(),
                entity.getCreatedAt()
        );
    }
    
    private static String maskDeviceToken(String token) {
        if (token == null || token.length() <= 8) {
            return "****";
        }
        return "****" + token.substring(token.length() - 8);
    }
}
