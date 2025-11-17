package pe.edu.upc.ParkUp.ParkUp_platform.logs.application.internal.queryservices;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetAllLogsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetLogByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.persistence.jpa.repositories.LogRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogQueryServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogQueryServiceImpl logQueryService;

    private GetLogByIdQuery getLogByIdQuery;
    private GetAllLogsQuery getAllLogsQuery;
    private Pageable pageable;
    private Log testLog;

    @BeforeEach
    void setUp() {
        getLogByIdQuery = new GetLogByIdQuery(1L);
        getAllLogsQuery = new GetAllLogsQuery(0, 20, "CREATE_USER", "admin", "SUCCESS", null, null, null);
        pageable = PageRequest.of(0, 20);

        testLog = new Log();
        testLog.setId(1L);
        testLog.setAction("CREATE_USER");
        testLog.setUserId(1L);
        testLog.setUsername("admin");
        testLog.setUserEmail("admin@example.com");
        testLog.setStatus("SUCCESS");
    }

    @Test
    void handle_GetLogByIdQuery_ReturnsLog() {
        // Arrange
        when(logRepository.findById(1L)).thenReturn(Optional.of(testLog));

        // Act
        Optional<Log> result = logQueryService.handle(getLogByIdQuery);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testLog.getId(), result.get().getId());
        assertEquals(testLog.getAction(), result.get().getAction());
        verify(logRepository, times(1)).findById(1L);
    }

    @Test
    void handle_GetLogByIdQuery_LogNotFound_ReturnsEmpty() {
        // Arrange
        when(logRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Log> result = logQueryService.handle(getLogByIdQuery);

        // Assert
        assertFalse(result.isPresent());
        verify(logRepository, times(1)).findById(1L);
    }

    @Test
    void handle_GetAllLogsQuery_ReturnsPageOfLogs() {
        // Arrange
        when(logRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), eq(pageable))).thenReturn(new PageImpl<>(Arrays.asList(testLog), pageable, 1));

        // Act
        Page<Log> result = logQueryService.handle(getAllLogsQuery, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testLog, result.getContent().get(0));
        verify(logRepository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), eq(pageable));
    }

    @Test
    void handle_GetAllLogsQuery_WithDateFilters_ReturnsPageOfLogs() {
        // Arrange
        String startDate = "2024-01-01T00:00:00";
        String endDate = "2024-01-31T23:59:59";
        GetAllLogsQuery queryWithDates = new GetAllLogsQuery(0, 20, null, null, null, startDate, endDate, null);
        
        // Set a timestamp for the test log that falls within the date range
        testLog.setTimestamp(LocalDateTime.parse("2024-01-15T12:00:00"));
        when(logRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), eq(pageable))).thenReturn(new PageImpl<>(Arrays.asList(testLog), pageable, 1));

        // Act
        Page<Log> result = logQueryService.handle(queryWithDates, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(logRepository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), eq(pageable));
    }
}