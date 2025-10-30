package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.GetUserProfileByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileQueryService;

import java.util.Optional;

/**
 * Anti-Corruption Layer service for Profile Bounded Context
 * Provides a facade to access user profile information from the notification context
 */
@Service
public class ProfileContextFacade {

    private final UserProfileQueryService userProfileQueryService;

    public ProfileContextFacade(UserProfileQueryService userProfileQueryService) {
        this.userProfileQueryService = userProfileQueryService;
    }

    /**
     * Gets the phone number (with country code) for a user
     *
     * @param userId The user ID
     * @return The full phone number in format +51987654321, or empty if not found
     */
    public Optional<String> getUserPhoneNumber(Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var profile = userProfileQueryService.handle(query);
        
        return profile.map(userProfile -> 
            userProfile.getPhoneNumber().getFullPhoneNumber()
        );
    }

    /**
     * Checks if user has notifications enabled
     *
     * @param userId The user ID
     * @return true if notifications are enabled, false otherwise
     */
    public boolean areNotificationsEnabled(Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var profile = userProfileQueryService.handle(query);
        
        return profile.map(userProfile -> 
            userProfile.getNotificationsEnabled()
        ).orElse(false);
    }

    /**
     * Checks if user has SMS notifications enabled
     *
     * @param userId The user ID
     * @return true if SMS notifications are enabled, false otherwise
     */
    public boolean areSmsNotificationsEnabled(Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var profile = userProfileQueryService.handle(query);
        
        return profile.map(userProfile -> 
            userProfile.getSmsNotificationsEnabled()
        ).orElse(false);
    }

    /**
     * Checks if user has email notifications enabled
     *
     * @param userId The user ID
     * @return true if email notifications are enabled, false otherwise
     */
    public boolean areEmailNotificationsEnabled(Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var profile = userProfileQueryService.handle(query);
        
        return profile.map(userProfile -> 
            userProfile.getEmailNotificationsEnabled()
        ).orElse(false);
    }

    /**
     * Gets the user's full name
     *
     * @param userId The user ID
     * @return The full name, or empty if not found
     */
    public Optional<String> getUserFullName(Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var profile = userProfileQueryService.handle(query);
        
        return profile.map(userProfile -> 
            userProfile.getFullName().getFullName()
        );
    }
}
