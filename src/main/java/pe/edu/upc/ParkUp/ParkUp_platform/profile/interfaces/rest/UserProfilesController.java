package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.UpdateNotificationSettingsCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.UpdateProfileImageCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.GetUserProfileByDocumentIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.GetUserProfileByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.GetUserProfileByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.*;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform.CreateUserProfileCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform.UpdateUserProfileCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform.UserProfileResourceFromEntityAssembler;

/**
 * REST Controller for User Profiles
 * Provides endpoints for managing user profiles and account settings
 */
@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "User Profiles", description = "User profile management endpoints")
public class UserProfilesController {

    private final UserProfileCommandService userProfileCommandService;
    private final UserProfileQueryService userProfileQueryService;

    public UserProfilesController(UserProfileCommandService userProfileCommandService,
                                 UserProfileQueryService userProfileQueryService) {
        this.userProfileCommandService = userProfileCommandService;
        this.userProfileQueryService = userProfileQueryService;
    }

    /**
     * Creates a new user profile
     *
     * @param resource The create user profile resource
     * @return The created user profile resource
     */
    @PostMapping
    @Operation(summary = "Create a new user profile", description = "Creates a new user profile with personal information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or profile already exists")
    })
    public ResponseEntity<UserProfileResource> createUserProfile(@RequestBody CreateUserProfileResource resource) {
        try {
            var command = CreateUserProfileCommandFromResourceAssembler.toCommandFromResource(resource);
            var userProfile = userProfileCommandService.handle(command);
            
            if (userProfile.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
            return new ResponseEntity<>(userProfileResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets a user profile by its ID
     *
     * @param profileId The profile ID
     * @return The user profile resource
     */
    @GetMapping("/{profileId}")
    @Operation(summary = "Get user profile by ID", description = "Retrieves a user profile by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> getUserProfileById(@PathVariable Long profileId) {
        var query = new GetUserProfileByIdQuery(profileId);
        var userProfile = userProfileQueryService.handle(query);
        
        if (userProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
        return ResponseEntity.ok(userProfileResource);
    }

    /**
     * Gets a user profile by user ID
     *
     * @param userId The user ID
     * @return The user profile resource
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user profile by user ID", description = "Retrieves a user profile by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> getUserProfileByUserId(@PathVariable Long userId) {
        var query = new GetUserProfileByUserIdQuery(userId);
        var userProfile = userProfileQueryService.handle(query);
        
        if (userProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
        return ResponseEntity.ok(userProfileResource);
    }

    /**
     * Gets a user profile by document number (DNI)
     *
     * @param dni The document number
     * @return The user profile resource
     */
    @GetMapping("/document/{dni}")
    @Operation(summary = "Get user profile by DNI", description = "Retrieves a user profile by document number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> getUserProfileByDocumentId(@PathVariable String dni) {
        var query = new GetUserProfileByDocumentIdQuery(dni);
        var userProfile = userProfileQueryService.handle(query);
        
        if (userProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
        return ResponseEntity.ok(userProfileResource);
    }

    /**
     * Updates an existing user profile
     *
     * @param profileId The profile ID
     * @param resource  The update user profile resource
     * @return The updated user profile resource
     */
    @PutMapping("/{profileId}")
    @Operation(summary = "Update user profile", description = "Updates an existing user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> updateUserProfile(@PathVariable Long profileId,
                                                                @RequestBody UpdateUserProfileResource resource) {
        try {
            var command = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
            var userProfile = userProfileCommandService.handle(command);
            
            if (userProfile.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
            return ResponseEntity.ok(userProfileResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates the profile image
     *
     * @param profileId The profile ID
     * @param resource  The update profile image resource
     * @return The updated user profile resource
     */
    @PatchMapping("/{profileId}/image")
    @Operation(summary = "Update profile image", description = "Updates the user's profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image updated successfully"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> updateProfileImage(@PathVariable Long profileId,
                                                                 @RequestBody UpdateProfileImageResource resource) {
        try {
            var command = new UpdateProfileImageCommand(profileId, resource.imageUrl());
            var userProfile = userProfileCommandService.handle(command);
            
            if (userProfile.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
            return ResponseEntity.ok(userProfileResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates notification settings
     *
     * @param profileId The profile ID
     * @param resource  The update notification settings resource
     * @return The updated user profile resource
     */
    @PatchMapping("/{profileId}/notifications")
    @Operation(summary = "Update notification settings", description = "Updates the user's notification preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification settings updated successfully"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileResource> updateNotificationSettings(@PathVariable Long profileId,
                                                                         @RequestBody UpdateNotificationSettingsResource resource) {
        try {
            var command = new UpdateNotificationSettingsCommand(
                    profileId,
                    resource.notificationsEnabled(),
                    resource.emailNotificationsEnabled(),
                    resource.smsNotificationsEnabled()
            );
            var userProfile = userProfileCommandService.handle(command);
            
            if (userProfile.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            var userProfileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(userProfile.get());
            return ResponseEntity.ok(userProfileResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
