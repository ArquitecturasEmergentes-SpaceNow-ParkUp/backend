package pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration properties for the Notification bounded context.
 * Loads configuration from application.properties.
 */
@Configuration
@ConfigurationProperties(prefix = "notification")
@Getter
@Setter
public class NotificationConfiguration {

    private PushConfig push = new PushConfig();
    private EmailConfig email = new EmailConfig();
    private WhatsAppConfig whatsapp = new WhatsAppConfig();

    @Getter
    @Setter
    public static class PushConfig {
        private boolean enabled = false;
    }

    @Getter
    @Setter
    public static class EmailConfig {
        private boolean enabled = false;
    }

    @Getter
    @Setter
    public static class WhatsAppConfig {
        private boolean enabled = false;
    }
}
