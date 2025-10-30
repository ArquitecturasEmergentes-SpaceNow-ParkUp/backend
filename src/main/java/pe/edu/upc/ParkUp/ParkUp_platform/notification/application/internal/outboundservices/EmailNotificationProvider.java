package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices;

import org.springframework.stereotype.Service;

/**
 * Email notification provider service
 * Handles sending emails via SendGrid or similar service
 */
@Service
public class EmailNotificationProvider {
    
    /**
     * Sends an email notification
     *
     * @param toEmail   The recipient email address
     * @param subject   The email subject
     * @param htmlBody  The email body (HTML)
     * @param plainText The email body (plain text fallback)
     * @return External email ID or null if failed
     */
    public String sendEmail(String toEmail, String subject, String htmlBody, String plainText) {
        // TODO: Implement SendGrid or similar email service integration
        
        try {
            // Simulated implementation
            System.out.println("=== EMAIL NOTIFICATION ===");
            System.out.println("To: " + toEmail);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + plainText);
            System.out.println("==========================");
            
            // Return a mock external ID
            return "email_" + System.currentTimeMillis();
            
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Validates an email address
     *
     * @param email The email address to validate
     * @return true if email is valid
     */
    public boolean validateEmail(String email) {
        // Basic email validation
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
