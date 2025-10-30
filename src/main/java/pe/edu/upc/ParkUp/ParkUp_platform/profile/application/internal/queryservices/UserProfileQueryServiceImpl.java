package pe.edu.upc.ParkUp.ParkUp_platform.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates.UserProfile;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.*;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.infrastructure.persistence.jpa.repositories.UserProfileRepository;

import java.util.Optional;

/**
 * Implementation of UserProfileQueryService
 * Handles query operations for user profiles
 */
@Service
public class UserProfileQueryServiceImpl implements UserProfileQueryService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileQueryServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Optional<UserProfile> handle(GetUserProfileByIdQuery query) {
        return userProfileRepository.findById(query.profileId());
    }

    @Override
    public Optional<UserProfile> handle(GetUserProfileByUserIdQuery query) {
        return userProfileRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<UserProfile> handle(GetUserProfileByDocumentIdQuery query) {
        return userProfileRepository.findByDocumentIdDocumentNumber(query.documentNumber());
    }
}
