package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.*;

import java.util.Optional;

/**
 * UserProfile command service interface
 * Handles all write operations for user profiles
 */
public interface UserProfileCommandService {

    /**
     * Creates a new user profile
     *
     * @param command The create user profile command
     * @return The created user profile
     */
    Optional<UserProfile> handle(CreateUserProfileCommand command);

    /**
     * Updates an existing user profile
     *
     * @param command The update user profile command
     * @return The updated user profile
     */
    Optional<UserProfile> handle(UpdateUserProfileCommand command);

    /**
     * Updates the profile image
     *
     * @param command The update profile image command
     * @return The updated user profile
     */
    Optional<UserProfile> handle(UpdateProfileImageCommand command);

    /**
     * Updates notification settings
     *
     * @param command The update notification settings command
     * @return The updated user profile
     */
    Optional<UserProfile> handle(UpdateNotificationSettingsCommand command);
}
