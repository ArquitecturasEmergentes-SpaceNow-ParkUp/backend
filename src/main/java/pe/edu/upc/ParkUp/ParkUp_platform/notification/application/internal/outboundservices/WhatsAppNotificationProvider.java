package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.commandservices.NotificationCommandServiceImpl;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.configuration.TwilioConfiguration;

/**
 * WhatsApp notification provider service using Twilio
 * Handles sending WhatsApp messages via Twilio API
 */
@Service
public class WhatsAppNotificationProvider {

    private final TwilioConfiguration twilioConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationCommandServiceImpl.class);

    public WhatsAppNotificationProvider(TwilioConfiguration twilioConfig) {
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
                LOGGER.info("✅ Twilio WhatsApp service initialized successfully");
            } else {
                LOGGER.warn("⚠️ Twilio credentials not configured. WhatsApp sending will fail.");
            }
        } catch (Exception e) {
            LOGGER.error("❌ Failed to initialize Twilio: " + e.getMessage());
        }
    }

    /**
     * Sends a WhatsApp message via Twilio
     *
     * @param phoneNumber The recipient phone number (e.g., +51986091617)
     * @param messageBody The WhatsApp message text
     * @return Twilio Message SID or null if failed
     */
    public String sendWhatsApp(String phoneNumber, String messageBody) {
        try {
            // Validate configuration
            if (twilioConfig.getAccountSid() == null || twilioConfig.getAuthToken() == null) {
                LOGGER.error("❌ Twilio not configured. Cannot send WhatsApp.");
                return null;
            }

            // Validate phone number format
            if (!validatePhoneNumber(phoneNumber)) {
                LOGGER.error("❌ Invalid phone number format: " + phoneNumber);
                return null;
            }

            // Format WhatsApp numbers (whatsapp:+number)
            String toWhatsApp = formatWhatsAppNumber(phoneNumber);
            String fromWhatsApp = formatWhatsAppNumber(twilioConfig.getFromNumber());

            // Create and send WhatsApp message
            Message message = Message.creator(
                    new PhoneNumber(toWhatsApp), // To: whatsapp:+51986091617
                    new PhoneNumber(fromWhatsApp), // From: whatsapp:+14155238886
                    messageBody // Message body
            ).create();

            LOGGER.info("✅ WhatsApp message sent successfully!");
            LOGGER.info("   SID: " + message.getSid());
            LOGGER.info("   To: " + toWhatsApp);
            LOGGER.info("   From: " + fromWhatsApp);
            LOGGER.info("   Status: " + message.getStatus());

            return message.getSid();

        } catch (Exception e) {
            LOGGER.error("❌ Failed to send WhatsApp via Twilio: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a WhatsApp message using a template
     * Templates must be pre-approved by WhatsApp/Twilio
     *
     * @param phoneNumber      The recipient phone number
     * @param contentSid       The Content Template SID
     * @param contentVariables JSON string with template variables
     * @return Twilio Message SID or null if failed
     */
    public String sendWhatsAppTemplate(String phoneNumber, String contentSid, String contentVariables) {
        try {
            // Validate configuration
            if (twilioConfig.getAccountSid() == null || twilioConfig.getAuthToken() == null) {
                LOGGER.error("❌ Twilio not configured. Cannot send WhatsApp.");
                return null;
            }

            // Format WhatsApp numbers
            String toWhatsApp = formatWhatsAppNumber(phoneNumber);
            String fromWhatsApp = formatWhatsAppNumber(twilioConfig.getFromNumber());

            // Create and send WhatsApp message with template
            Message message = Message.creator(
                    new PhoneNumber(toWhatsApp),
                    new PhoneNumber(fromWhatsApp),
                    "" // Empty body - content comes from template
            )
                    .setContentSid(contentSid)
                    .setContentVariables(contentVariables)
                    .create();

            LOGGER.info("✅ WhatsApp template message sent successfully!");
            LOGGER.info("   SID: " + message.getSid());
            LOGGER.info("   Template: " + contentSid);

            return message.getSid();

        } catch (Exception e) {
            LOGGER.error("❌ Failed to send WhatsApp template: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Formats a phone number for WhatsApp
     * Converts +51986091617 to whatsapp:+51986091617
     */
    private String formatWhatsAppNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        // If already has whatsapp: prefix, return as is
        if (phoneNumber.startsWith("whatsapp:")) {
            return phoneNumber;
        }
        // Add whatsapp: prefix
        return "whatsapp:" + phoneNumber;
    }

    /**
     * Validates a phone number format (with or without whatsapp: prefix)
     *
     * @param phoneNumber The phone number to validate
     * @return true if phone number is in valid E.164 format
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        // Remove whatsapp: prefix if present
        String cleanNumber = phoneNumber.replace("whatsapp:", "");

        // E.164 format: +[country code][number]
        // Example: +51987654321 (Peru), +14155238886 (Twilio Sandbox)
        return cleanNumber.matches("^\\+[1-9]\\d{1,14}$");
    }
}
