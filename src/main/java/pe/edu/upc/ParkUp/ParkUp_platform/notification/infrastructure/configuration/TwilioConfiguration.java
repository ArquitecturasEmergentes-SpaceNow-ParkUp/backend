package pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

/**
 * Configuration properties for Twilio WhatsApp service.
 * Loads Twilio credentials and settings from application.properties.
 */
@Configuration
@Getter
public class TwilioConfiguration {

    /**
     * Twilio Account SID
     */
    @Value("${twilio.account-sid}")
    private String accountSid;

    /**
     * Twilio Auth Token
     */
    @Value("${twilio.auth-token}")
    private String authToken;

    /**
     * Twilio phone number (sender)
     */
    @Value("${twilio.from-number}")
    private String fromNumber;
}
