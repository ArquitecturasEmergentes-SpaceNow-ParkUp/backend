package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetUserByEmailQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.UserResource;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries.GetUserProfileByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.services.UserProfileQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources.UserProfileResource;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.transform.UserProfileResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.CurrentUserResource;

import java.util.List;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 **/
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
  private final UserQueryService userQueryService;
  private final pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserCommandService userCommandService;
  private final UserProfileQueryService userProfileQueryService;

  public UsersController(UserQueryService userQueryService,
      pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserCommandService userCommandService,
      UserProfileQueryService userProfileQueryService) {
    this.userQueryService = userQueryService;
    this.userCommandService = userCommandService;
    this.userProfileQueryService = userProfileQueryService;
  }

  /**
   * This method returns all the users.
   *
   * @return a list of user resources.
   * @see UserResource
   */
  @GetMapping
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var getAllUsersQuery = new GetAllUsersQuery();
    var users = userQueryService.handle(getAllUsersQuery);
    var userResources = users.stream()
        .map(UserResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(userResources);
  }

  /**
   * This method returns the user with the given id.
   *
   * @param userId the user id.
   * @return the user resource with the given id
   * @throws RuntimeException if the user is not found
   * @see UserResource
   */
  @GetMapping(value = "/{userId}")
  public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var user = userQueryService.handle(getUserByIdQuery);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }

  /**
   * Returns the currently authenticated user with optional profile information.
   * 
   * @return CurrentUserResource containing id, email, roles and profile if
   *         available
   */
  @GetMapping(value = "/me")
  public ResponseEntity<CurrentUserResource> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      return ResponseEntity.status(401).build();
    }

    var userOpt = userQueryService.handle(new GetUserByEmailQuery(authentication.getName()));
    if (userOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var user = userOpt.get();
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user);

    UserProfileResource profileResource = null;
    var profileOpt = userProfileQueryService.handle(new GetUserProfileByUserIdQuery(user.getId()));
    if (profileOpt.isPresent()) {
      profileResource = UserProfileResourceFromEntityAssembler.toResourceFromEntity(profileOpt.get());
    }

    var currentUser = new CurrentUserResource(
        userResource.id(), userResource.email(), userResource.roles(), profileResource);
    return ResponseEntity.ok(currentUser);
  }

  @org.springframework.web.bind.annotation.PutMapping(value = "/{userId}/disability")
  public ResponseEntity<UserResource> updateDisabilityStatus(@PathVariable Long userId,
      @org.springframework.web.bind.annotation.RequestBody pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.UpdateUserDisabilityStatusResource resource) {
    var command = new pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.UpdateUserDisabilityStatusCommand(
        userId, resource.disability());
    var user = userCommandService.handle(command);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }
}
