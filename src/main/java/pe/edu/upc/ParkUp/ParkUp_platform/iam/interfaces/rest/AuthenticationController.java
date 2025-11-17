package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetUserByEmailQuery;
import jakarta.servlet.http.HttpServletRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignInResource;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignUpResource;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.UserResource;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogDetailsService;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

  private final UserCommandService userCommandService;
  private final LogCommandService logCommandService;
  private final UserQueryService userQueryService;
  private final LogDetailsService logDetailsService;

  public AuthenticationController(UserCommandService userCommandService,
                                  LogCommandService logCommandService,
                                  UserQueryService userQueryService,
                                  LogDetailsService logDetailsService) {
    this.userCommandService = userCommandService;
    this.logCommandService = logCommandService;
    this.userQueryService = userQueryService;
    this.logDetailsService = logDetailsService;
  }

  /**
   * Handles the sign-in request.
   * @param signInResource the sign-in request body.
   * @return the authenticated user resource.
   */
  @PostMapping("/login")
    public ResponseEntity<AuthenticatedUserResource> signIn(
      @RequestBody SignInResource signInResource,
      HttpServletRequest request) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    String payloadHash = computePayloadHash(signInResource.email() + ":" + signInResource.password());

    if (authenticatedUser.isEmpty()) {
      // Attempt to find user id for failed login (if user exists) - don't expose existence to client
      Long userId = userQueryService.handle(new GetUserByEmailQuery(signInResource.email()))
          .map(u -> u.getId()).orElse(0L);

          var failedLog = logDetailsService.buildAuthLog(
            "AUTH_LOGIN",
            userId,
            signInResource.email(),
            signInResource.email(),
            "Failed sign-in attempt",
            request,
            "FAILURE",
            payloadHash,
            userId
          );
      try {
        logCommandService.handle(failedLog);
      } catch (Exception e) {
        System.err.println("Failed to create authentication failure log: " + e.getMessage());
      }

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(
            authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());

    // Create a log entry for successful sign in
    try {
      var user = authenticatedUser.get().getLeft();
      String ipAddress = request.getHeader("X-Forwarded-For");
      if (ipAddress == null || ipAddress.isBlank()) {
        ipAddress = request.getRemoteAddr();
      }

          var command = logDetailsService.buildAuthLog(
            "AUTH_LOGIN",
            user.getId(),
            user.getEmail(),
            user.getEmail(),
            "User logged in successfully",
            request,
            "SUCCESS",
            payloadHash,
            user.getId()
          );
          logCommandService.handle(command);
    } catch (Exception e) {
      // If logging fails, don't impact authentication response
      System.err.println("Failed to create authentication log: " + e.getMessage());
    }
    return ResponseEntity.ok(authenticatedUserResource);
  }

  private static String computePayloadHash(String payload) {
    if (payload == null) return null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(payload.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Handles the sign-up request.
   * @param signUpResource the sign-up request body.
   * @return the created user resource.
   */
  @PostMapping("/register")
  public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource, HttpServletRequest request) {
    var signUpCommand = SignUpCommandFromResourceAssembler
        .toCommandFromResource(signUpResource);
    var user = userCommandService.handle(signUpCommand);
    String payloadHash = computePayloadHash(signUpResource.email() + ":" + signUpResource.password());

    if (user.isEmpty()) {
            // log the failed sign-up
            try {
                var failedLog = logDetailsService.buildAuthLog(
                  "AUTH_SIGNUP",
                  0L,
                  signUpResource.email(),
                  signUpResource.email(),
                  "Failed sign-up attempt",
                  request,
                  "FAILURE",
                  payloadHash,
                  0L
                );
              logCommandService.handle(failedLog);
            } catch (Exception e) {
              System.err.println("Failed to create authentication signup failure log: " + e.getMessage());
            }
      return ResponseEntity.badRequest().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    try {
        var successLog = logDetailsService.buildAuthLog(
          "AUTH_SIGNUP",
          userResource.id(),
          userResource.email(),
          userResource.email(),
          "User registered successfully",
          request,
          "SUCCESS",
          payloadHash,
          userResource.id()
        );
        logCommandService.handle(successLog);
    } catch (Exception e) {
      System.err.println("Failed to create authentication signup success log: " + e.getMessage());
    }
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }
}
