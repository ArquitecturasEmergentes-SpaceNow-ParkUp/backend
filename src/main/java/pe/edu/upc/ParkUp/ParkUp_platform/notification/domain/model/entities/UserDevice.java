package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.entities.AuditableModel;

/**
 * User device entity
 * Represents a device registered by a user for receiving push notifications
 */
@Entity
@Table(name = "user_devices")
@Getter
public class UserDevice extends AuditableModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private Long userId;
    
    @NotBlank
    @Column(nullable = false, unique = true, length = 500)
    private String deviceToken;
    
    @Column(length = 50)
    private String deviceType; // IOS, ANDROID, WEB
    
    @Column(length = 100)
    private String deviceName;
    
    @Column
    private Boolean isActive;
    
    protected UserDevice() {
    }
    
    public UserDevice(Long userId, String deviceToken, String deviceType, String deviceName) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Device token is required");
        }
        
        this.userId = userId;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.isActive = true;
    }
    
    /**
     * Deactivates the device (e.g., when user logs out or uninstalls app)
     */
    public void deactivate() {
        this.isActive = false;
    }
    
    /**
     * Reactivates the device
     */
    public void activate() {
        this.isActive = true;
    }
    
    /**
     * Updates the device token
     */
    public void updateToken(String newToken) {
        if (newToken == null || newToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Device token cannot be empty");
        }
        this.deviceToken = newToken;
    }
}
