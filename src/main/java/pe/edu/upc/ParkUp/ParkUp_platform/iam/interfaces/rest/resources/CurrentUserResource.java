package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources;

import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.UserProfileResource;

import java.util.List;

/**
 * Resource representing the currently authenticated user, including profile information if available.
 */
public record CurrentUserResource(Long id, String email, List<String> roles, boolean disability, UserProfileResource profile) {}