package pe.edu.upc.ParkUp.ParkUp_platform.logs.application.internal.commandservices;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.persistence.jpa.repositories.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogCommandServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogCommandServiceImpl logCommandService;

    private CreateLogCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateLogCommand(
            "CREATE_USER",
            1L,
            "admin",
            "admin@example.com",
            "Created new user",
            "192.168.1.1",
            "Mozilla/5.0",
            "USER",
            123L,
            "SUCCESS",
            null
        );
    }

    @Test
    void handle_ValidCommand_CreatesLog() {
        // Arrange
        Log expectedLog = new Log();
        expectedLog.setId(1L);
        expectedLog.setAction(command.action());
        expectedLog.setUserId(command.userId());
        expectedLog.setUsername(command.username());
        expectedLog.setUserEmail(command.userEmail());
        expectedLog.setDetails(command.details());
        expectedLog.setIpAddress(command.ipAddress());
        expectedLog.setUserAgent(command.userAgent());
        expectedLog.setResourceType(command.resourceType());
        expectedLog.setResourceId(command.resourceId());
        expectedLog.setStatus(command.status());

        when(logRepository.save(any(Log.class))).thenReturn(expectedLog);

        // Act
        Log result = logCommandService.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedLog.getId(), result.getId());
        assertEquals(command.action(), result.getAction());
        assertEquals(command.userId(), result.getUserId());
        assertEquals(command.username(), result.getUsername());
        assertEquals(command.userEmail(), result.getUserEmail());
        assertEquals(command.details(), result.getDetails());
        assertEquals(command.ipAddress(), result.getIpAddress());
        assertEquals(command.userAgent(), result.getUserAgent());
        assertEquals(command.resourceType(), result.getResourceType());
        assertEquals(command.resourceId(), result.getResourceId());
        assertEquals(command.status(), result.getStatus());

        verify(logRepository, times(1)).save(any(Log.class));
    }

    @Test
    void handle_NullCommand_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            logCommandService.handle(null);
        });
    }
}