package pe.edu.upc.ParkUp.ParkUp_platform.profile.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.*;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.infrastructure.persistence.jpa.repositories.UserProfileRepository;

import java.util.Optional;

/**
 * Implementation of UserProfileCommandService
 * Handles command operations for user profiles
 */
@Service
public class UserProfileCommandServiceImpl implements UserProfileCommandService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileCommandServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Optional<UserProfile> handle(CreateUserProfileCommand command) {
        // Check if user already has a profile
        if (userProfileRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("User already has a profile");
        }

        // Check if DNI is already registered
        if (userProfileRepository.existsByDocumentIdDocumentNumber(command.dni())) {
            throw new IllegalArgumentException("Document number is already registered");
        }

        var userProfile = new UserProfile(command);
        var createdProfile = userProfileRepository.save(userProfile);
        return Optional.of(createdProfile);
    }

    @Override
    public Optional<UserProfile> handle(UpdateUserProfileCommand command) {
        var userProfileOptional = userProfileRepository.findById(command.profileId());
        
        if (userProfileOptional.isEmpty()) {
            throw new IllegalArgumentException("User profile not found");
        }

        var userProfile = userProfileOptional.get();

        // Check if DNI is being changed and if new DNI is already registered
        if (!userProfile.getDocumentId().getDocumentNumber().equals(command.dni())) {
            if (userProfileRepository.existsByDocumentIdDocumentNumber(command.dni())) {
                throw new IllegalArgumentException("Document number is already registered");
            }
            userProfile.updateDocumentId(command.dni());
        }

        userProfile.updateFullName(command.firstName(), command.lastName());
        userProfile.updatePhoneNumber(command.countryCode(), command.phoneNumber());

        var updatedProfile = userProfileRepository.save(userProfile);
        return Optional.of(updatedProfile);
    }

    @Override
    public Optional<UserProfile> handle(UpdateProfileImageCommand command) {
        var userProfileOptional = userProfileRepository.findById(command.profileId());
        
        if (userProfileOptional.isEmpty()) {
            throw new IllegalArgumentException("User profile not found");
        }

        var userProfile = userProfileOptional.get();
        userProfile.updateProfileImage(command.imageUrl());

        var updatedProfile = userProfileRepository.save(userProfile);
        return Optional.of(updatedProfile);
    }

    @Override
    public Optional<UserProfile> handle(UpdateNotificationSettingsCommand command) {
        var userProfileOptional = userProfileRepository.findById(command.profileId());
        
        if (userProfileOptional.isEmpty()) {
            throw new IllegalArgumentException("User profile not found");
        }

        var userProfile = userProfileOptional.get();

        if (command.notificationsEnabled() != null) {
            userProfile.setNotificationsEnabled(command.notificationsEnabled());
        }
        if (command.emailNotificationsEnabled() != null) {
            userProfile.setEmailNotificationsEnabled(command.emailNotificationsEnabled());
        }
        if (command.smsNotificationsEnabled() != null) {
            userProfile.setSmsNotificationsEnabled(command.smsNotificationsEnabled());
        }

        var updatedProfile = userProfileRepository.save(userProfile);
        return Optional.of(updatedProfile);
    }
}
