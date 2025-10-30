package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.configuration.TwilioConfiguration;

/**
 * SMS notification provider service using Twilio
 * Handles sending SMS via Twilio API
 */
@Service
public class SmsNotificationProvider {
    
    private final TwilioConfiguration twilioConfig;
    
    public SmsNotificationProvider(TwilioConfiguration twilioConfig) {
        this.twilioConfig = twilioConfig;
    }
    
    /**
     * Initialize Twilio client with credentials
     */
    @PostConstruct
    public void init() {
        try {
            if (twilioConfig.getAccountSid() != null && twilioConfig.getAuthToken() != null) {
                Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
                System.out.println("✅ Twilio SMS service initialized successfully");
            } else {
                System.err.println("⚠️ Twilio credentials not configured. SMS sending will fail.");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Twilio: " + e.getMessage());
        }
    }
    
    /**
     * Sends an SMS notification via Twilio
     *
     * @param phoneNumber The recipient phone number (must include country code, e.g., +51987654321)
     * @param messageBody The SMS message (max 160 characters recommended)
     * @return Twilio Message SID or null if failed
     */
    public String sendSms(String phoneNumber, String messageBody) {
        try {
            // Validate configuration
            if (twilioConfig.getAccountSid() == null || twilioConfig.getAuthToken() == null) {
                System.err.println("❌ Twilio not configured. Cannot send SMS.");
                return null;
            }
            
            // Validate phone number format
            if (!validatePhoneNumber(phoneNumber)) {
                System.err.println("❌ Invalid phone number format: " + phoneNumber);
                return null;
            }
            
            // Create and send message
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),           // To
                    new PhoneNumber(twilioConfig.getFromNumber()),  // From
                    messageBody                              // Body
            ).create();
            
            System.out.println("✅ SMS sent successfully!");
            System.out.println("   SID: " + message.getSid());
            System.out.println("   To: " + phoneNumber);
            System.out.println("   Status: " + message.getStatus());
            
            return message.getSid();
            
        } catch (Exception e) {
            System.err.println("❌ Failed to send SMS via Twilio: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Validates a phone number format
     *
     * @param phoneNumber The phone number to validate
     * @return true if phone number is in valid E.164 format (+[country code][number])
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        // E.164 format: +[country code][number]
        // Example: +51987654321 (Peru), +1234567890 (US)
        return phoneNumber != null && phoneNumber.matches("^\\+[1-9]\\d{1,14}$");
    }
}
