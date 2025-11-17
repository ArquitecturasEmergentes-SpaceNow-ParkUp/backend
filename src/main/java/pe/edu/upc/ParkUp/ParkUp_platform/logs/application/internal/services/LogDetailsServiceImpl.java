package pe.edu.upc.ParkUp.ParkUp_platform.logs.application.internal.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogDetailsService;

 

@Service
public class LogDetailsServiceImpl implements LogDetailsService {

    @Override
    public CreateLogCommand buildAuthLog(String action, Long userId, String username, String userEmail, String details, HttpServletRequest request, String status, String requestPayloadHash, Long resourceId) {
        String ip = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;

        return new CreateLogCommand(
                action,
                userId,
                username,
                userEmail,
                details,
                ip,
                userAgent,
                "USER",
                resourceId,
                status,
                requestPayloadHash
        );
    }

    @Override
    public CreateLogCommand buildFromRequest(HttpServletRequest request, HttpServletResponse response, String username, Long userId, Exception ex, String requestPayloadHash, Long startTimeMillis) {
        String requestUri = request.getRequestURI();
        String details = String.format("Method: %s, URI: %s, Status: %d", request.getMethod(), requestUri, response.getStatus());
        if (ex != null) {
            details += ", Error: " + ex.getMessage();
        }

        String action = determineAction(request.getMethod(), requestUri);
        String status = determineStatus(response.getStatus(), ex);
        String ip = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        if (startTimeMillis != null) {
            long executionTime = System.currentTimeMillis() - startTimeMillis;
            details += ", Execution Time: " + executionTime + "ms";
        }

        return new CreateLogCommand(
                action,
                userId,
                username,
                username,
                details,
                ip,
                userAgent,
                determineResourceType(requestUri),
                extractResourceId(requestUri),
                status,
                requestPayloadHash
        );
    }

    private String determineAction(String method, String uri) {
        String baseAction = switch (method) {
            case "GET" -> "VIEW";
            case "POST" -> "CREATE";
            case "PUT", "PATCH" -> "UPDATE";
            case "DELETE" -> "DELETE";
            default -> "OPERATION";
        };
        return baseAction + "_" + determineResourceType(uri);
    }

    private String determineResourceType(String uri) {
        if (uri.contains("/users")) return "USER";
        if (uri.contains("/roles")) return "ROLE";
        if (uri.contains("/parking-lots")) return "PARKING_LOT";
        if (uri.contains("/recognition-units")) return "RECOGNITION_UNIT";
        if (uri.contains("/extraordinary-cases")) return "EXTRAORDINARY_CASE";
        if (uri.contains("/admin")) return "ADMIN_RESOURCE";
        return "RESOURCE";
    }

    private Long extractResourceId(String uri) {
        String[] parts = uri.split("/");
        if (parts.length >= 4) {
            try {
                return Long.parseLong(parts[parts.length - 1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private String determineStatus(int statusCode, Exception ex) {
        if (ex != null) return "FAILURE";
        if (statusCode >= 200 && statusCode < 300) return "SUCCESS";
        if (statusCode >= 400 && statusCode < 500) return "WARNING";
        if (statusCode >= 500) return "FAILURE";
        return "SUCCESS";
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
