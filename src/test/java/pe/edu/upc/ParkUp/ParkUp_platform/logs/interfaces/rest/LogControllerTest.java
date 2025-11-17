package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetAllLogsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetLogByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.CreateLogResource;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.LogResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.mock.web.MockHttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Sort;
import jakarta.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogControllerTest {

    @Mock
    private LogCommandService logCommandService;

    @Mock
    private LogQueryService logQueryService;

    @InjectMocks
    private LogController logController;

    private Log testLog;
    private CreateLogResource createLogResource;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testLog = new Log();
        testLog.setId(1L);
        testLog.setTimestamp(LocalDateTime.now());
        testLog.setAction("CREATE_USER");
        testLog.setUserId(1L);
        testLog.setUsername("admin");
        testLog.setUserEmail("admin@example.com");
        testLog.setDetails("Created new user");
        testLog.setIpAddress("192.168.1.1");
        testLog.setUserAgent("Mozilla/5.0");
        testLog.setResourceType("USER");
        testLog.setResourceId(123L);
        testLog.setStatus("SUCCESS");

        createLogResource = new CreateLogResource(
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

        pageable = PageRequest.of(0, 20);
    }

    @Test
    void getAllLogs_ReturnsPageOfLogs() {
        // Arrange
        Page<Log> logPage = new PageImpl<>(Arrays.asList(testLog));
        when(logQueryService.handle(any(GetAllLogsQuery.class), eq(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "action"))))).thenReturn(logPage);

        // Act
        MockHttpServletRequest request = new MockHttpServletRequest();
        // Example of a JSON-encoded array coming from some clients
        request.setParameter("sort", "[\"action\",\"desc\"]");

        ResponseEntity<Page<LogResource>> response = logController.getAllLogs(
                null, null, null, null, null, null, pageable
            , request
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        verify(logQueryService, times(1)).handle(any(GetAllLogsQuery.class), eq(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "action"))));
    }

    @Test
    void getAllLogs_UnknownSortProperty_Ignored() {
        // Arrange
        Page<Log> logPage = new PageImpl<>(Arrays.asList(testLog));
        Pageable pageable = PageRequest.of(0, 20);
        MockHttpServletRequest request = new MockHttpServletRequest();
        // Some clients can pass sort=string which is not a field
        request.setParameter("sort", "string");

        when(logQueryService.handle(any(GetAllLogsQuery.class), eq(pageable))).thenReturn(logPage);

        // Act
        ResponseEntity<Page<LogResource>> response = logController.getAllLogs(
                null, null, null, null, null, null, pageable, request
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        verify(logQueryService, times(1)).handle(any(GetAllLogsQuery.class), eq(pageable));
    }

    @Test
    void getLogById_LogExists_ReturnsLog() {
        // Arrange
        when(logQueryService.handle(any(GetLogByIdQuery.class))).thenReturn(Optional.of(testLog));

        // Act
        ResponseEntity<LogResource> response = logController.getLogById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLog.getId(), response.getBody().id());
        verify(logQueryService, times(1)).handle(any(GetLogByIdQuery.class));
    }

    @Test
    void getLogById_LogNotFound_ReturnsNotFound() {
        // Arrange
        when(logQueryService.handle(any(GetLogByIdQuery.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<LogResource> response = logController.getLogById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(logQueryService, times(1)).handle(any(GetLogByIdQuery.class));
    }

    @Test
    void createLog_ValidResource_ReturnsCreatedLog() {
        // Arrange
        when(logCommandService.handle(any(CreateLogCommand.class))).thenReturn(testLog);

        // Act
        ResponseEntity<LogResource> response = logController.createLog(createLogResource);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLog.getId(), response.getBody().id());
        verify(logCommandService, times(1)).handle(any(CreateLogCommand.class));
    }
}