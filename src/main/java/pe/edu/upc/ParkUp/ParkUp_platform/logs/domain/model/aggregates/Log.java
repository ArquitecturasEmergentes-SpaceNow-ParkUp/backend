package pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs",
       indexes = {
           @Index(name = "idx_logs_timestamp", columnList = "timestamp"),
           @Index(name = "idx_logs_action", columnList = "action"),
           @Index(name = "idx_logs_user", columnList = "user_id")
       })
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Log {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "timestamp", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime timestamp;
    
    @Column(name = "action", nullable = false, length = 100)
    private String action;
    
    @Column(name = "user_id", nullable = true)
    private Long userId;
    
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    
    @Column(name = "user_email", nullable = false, length = 255)
    private String userEmail;
    
    @Column(name = "details")
    @Lob
    private String details;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    @Column(name = "resource_type", length = 50)
    private String resourceType;
    
    @Column(name = "resource_id")
    private Long resourceId;
    
    @Column(name = "status", length = 20)
    private String status; // SUCCESS, FAILURE, WARNING
    
    @Column(name = "error_message")
    @Lob
    private String errorMessage;
    
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "request_payload_hash", length = 128)
    private String requestPayloadHash;
    
    public Log() {}
    
    public Log(String action, Long userId, String username, String userEmail, 
               String details, String ipAddress, String userAgent, 
               String resourceType, Long resourceId, String status) {
        this.action = action;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.details = details;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.status = status;
    }

    public Log(String action, Long userId, String username, String userEmail,
               String details, String ipAddress, String userAgent,
               String resourceType, Long resourceId, String status,
               String requestPayloadHash) {
        this(action, userId, username, userEmail, details, ipAddress, userAgent, resourceType, resourceId, status);
        this.requestPayloadHash = requestPayloadHash;
    }
    
    public void setErrorDetails(String errorMessage) {
        this.errorMessage = errorMessage;
        this.status = "FAILURE";
    }
    
    public void setSuccess() {
        this.status = "SUCCESS";
    }
    
    public void setWarning(String warningMessage) {
        this.status = "WARNING";
        this.errorMessage = warningMessage;
    }
}