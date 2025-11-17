package pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.middleware;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services.UserQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogDetailsService;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.queries.GetUserByEmailQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class AdminLoggingInterceptor implements HandlerInterceptor {
    
    private final LogCommandService logCommandService;
    private final UserQueryService userQueryService;
    private final LogDetailsService logDetailsService;
    
    private static final List<String> ADMIN_PATHS = Arrays.asList(
        "/api/admin",
        "/api/users",
        "/api/roles",
        "/api/parking-lots",
        "/api/recognition-units",
        "/api/extraordinary-cases"
    );
    
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
        "/api/logs",
        "/api/auth/me",
        "/swagger-ui",
        "/v3/api-docs"
    );
    
    public AdminLoggingInterceptor(LogCommandService logCommandService, UserQueryService userQueryService, LogDetailsService logDetailsService) {
        this.logCommandService = logCommandService;
        this.userQueryService = userQueryService;
        this.logDetailsService = logDetailsService;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestUri = request.getRequestURI();
        
        // Check if this is an admin path and not excluded
        boolean isAdminPath = ADMIN_PATHS.stream().anyMatch(requestUri::startsWith);
        boolean isExcluded = EXCLUDED_PATHS.stream().anyMatch(requestUri::startsWith);
        
        if (!isAdminPath || isExcluded) {
            return;
        }
        
        // Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }
        
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl userDetails)) {
            return;
        }
        
        // Extract user information from the authentication object
        String username = userDetails.getUsername();
        Long userId = null;
        // Try to resolve user id from the authentication name (email)
        var userOpt = userQueryService.handle(new GetUserByEmailQuery(username));
        if (userOpt.isPresent()) {
            userId = userOpt.get().getId();
        }
        // userEmail equals username in this implementation
        
        // Calculate execution time
        Long startTime = (Long) request.getAttribute("startTime");
        // Execution time is calculated by LogDetailsService using startTime
        
        // Build log details via LogDetailsService
        
        // Create log command
        // userId can be null (logging anonymous admin actions) - the Log entity allows null userId

        CreateLogCommand command = logDetailsService.buildFromRequest(request, response, username, userId, ex, null, startTime);
        
        // Log asynchronously to avoid blocking the response
        new Thread(() -> {
            try {
                logCommandService.handle(command);
            } catch (Exception e) {
                // Log the error but don't throw it to avoid affecting the main request
                System.err.println("Failed to create admin log: " + e.getMessage());
            }
        }).start();
    }
    
    // Helper methods for action/resource/status determination moved to LogDetailsService
}