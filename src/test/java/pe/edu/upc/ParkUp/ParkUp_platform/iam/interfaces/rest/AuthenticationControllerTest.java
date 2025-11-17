package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.aggregates.User;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignInResource;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private UserCommandService userCommandService;

    @Mock
    private LogCommandService logCommandService;

    @Mock
    private pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogDetailsService logDetailsService;

    @Mock
    private pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserQueryService userQueryService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private User testUser;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.addHeader("User-Agent", "JUnit-Agent");
        request.addHeader("X-Forwarded-For", "127.0.0.1");

        testUser = mock(User.class);
    }

    @Test
    void signUp_Success_LogsEvent() {
        var newUser = mock(pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.aggregates.User.class);
        when(newUser.getId()).thenReturn(50L);
        when(newUser.getEmail()).thenReturn("newuser@example.com");
        when(userCommandService.handle(any(SignUpCommand.class))).thenReturn(Optional.of(newUser));

        when(logDetailsService.buildAuthLog(anyString(), anyLong(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), anyLong()))
            .thenReturn(new pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand("AUTH_SIGNUP", 50L, "newuser@example.com", "newuser@example.com", "User registered successfully", "127.0.0.1", "JUnit-Agent", "USER", 50L, "SUCCESS", null));

        var response = authenticationController.signUp(new pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignUpResource("newuser@example.com", "pw", null), request);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        verify(logCommandService, times(1)).handle(any());
    }

    @Test
    void signUp_Failure_LogsEvent() {
        when(userCommandService.handle(any(SignUpCommand.class))).thenReturn(Optional.empty());

        when(logDetailsService.buildAuthLog(anyString(), anyLong(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), anyLong()))
            .thenReturn(new pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand("AUTH_SIGNUP", 0L, "user@example.com", "user@example.com", "Failed sign-up attempt", "127.0.0.1", "JUnit-Agent", "USER", 0L, "FAILURE", null));

        var response = authenticationController.signUp(new pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignUpResource("user@example.com", "pw", null), request);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        verify(logCommandService, times(1)).handle(any());
    }

    @Test
    void signIn_Success_LogsEvent() {
        when(testUser.getId()).thenReturn(42L);
        when(testUser.getEmail()).thenReturn("user@example.com");
        var pair = new ImmutablePair<>(testUser, "FAKE_TOKEN");
        when(userCommandService.handle(any(SignInCommand.class))).thenReturn(Optional.of(pair));

        when(logDetailsService.buildAuthLog(anyString(), anyLong(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), anyLong()))
            .thenReturn(new pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand("AUTH_LOGIN", 42L, "user@example.com", "user@example.com", "User logged in successfully", "127.0.0.1", "JUnit-Agent", "USER", 42L, "SUCCESS", null));

        var response = authenticationController.signIn(new SignInResource("user@example.com", "pw"), request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        // verify that logs were created
        verify(logCommandService, times(1)).handle(any());
    }

    @Test
    void signIn_Failure_LogsEvent() {
        when(userCommandService.handle(any(SignInCommand.class))).thenReturn(Optional.empty());
        when(userQueryService.handle(any(pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetUserByEmailQuery.class))).thenReturn(Optional.empty());

        when(logDetailsService.buildAuthLog(anyString(), anyLong(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), anyLong()))
            .thenReturn(new pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand("AUTH_LOGIN", 0L, "user@example.com", "user@example.com", "Failed sign-in attempt", "127.0.0.1", "JUnit-Agent", "USER", 0L, "FAILURE", null));

        var response = authenticationController.signIn(new SignInResource("user@example.com", "badpw"), request);

        assertNotNull(response);
        assertEquals(401, response.getStatusCode().value());
        verify(logCommandService, times(1)).handle(any());
    }
}
