package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.*;

import java.util.Optional;

/**
 * UserProfile query service interface
 * Handles all read operations for user profiles
 */
public interface UserProfileQueryService {

    /**
     * Gets a user profile by its ID
     *
     * @param query The get user profile by ID query
     * @return The user profile if found
     */
    Optional<UserProfile> handle(GetUserProfileByIdQuery query);

    /**
     * Gets a user profile by user ID
     *
     * @param query The get user profile by user ID query
     * @return The user profile if found
     */
    Optional<UserProfile> handle(GetUserProfileByUserIdQuery query);

    /**
     * Gets a user profile by document ID (DNI)
     *
     * @param query The get user profile by document ID query
     * @return The user profile if found
     */
    Optional<UserProfile> handle(GetUserProfileByDocumentIdQuery query);
}
