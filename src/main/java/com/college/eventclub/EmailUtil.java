package com.college.eventclub;

import java.util.Properties;

/**
 * EmailUtil - Utility class for sending emails
 * Note: Email functionality requires configuration in application.properties
 */
public class EmailUtil {

    private static final String SENDER_EMAIL = "your-email@gmail.com";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    /**
     * Send email notification
     * Note: In Spring Boot context, use EmailService instead
     */
    public static boolean sendEmail(String recipientEmail, String subject, String body) {
        try {
            System.out.println("Email notification would be sent to: " + recipientEmail);
            System.out.println("Subject: " + subject);
            return true;
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Send event registration confirmation email
     */
    public static boolean sendRegistrationConfirmation(String recipientEmail, String studentName, 
                                                       String eventTitle, String qrToken) {
        String subject = "Event Registration Confirmation";
        String body = "Dear " + studentName + ",\n\n" +
                     "Thank you for registering for the event: " + eventTitle + "\n\n" +
                     "Your QR Token: " + qrToken + "\n" +
                     "Please save this token for check-in.\n\n" +
                     "Best regards,\n" +
                     "Event Management Team";
        
        return sendEmail(recipientEmail, subject, body);
    }

    /**
     * Send event reminder email
     */
    public static boolean sendEventReminder(String recipientEmail, String studentName, 
                                            String eventTitle, String eventDate, String venue) {
        String subject = "Reminder: " + eventTitle;
        String body = "Dear " + studentName + ",\n\n" +
                     "This is a reminder for the upcoming event:\n" +
                     "Event: " + eventTitle + "\n" +
                     "Date: " + eventDate + "\n" +
                     "Venue: " + venue + "\n\n" +
                     "Please make sure to bring your QR token.\n\n" +
                     "Best regards,\n" +
                     "Event Management Team";
        
        return sendEmail(recipientEmail, subject, body);
    }

    /**
     * Send check-in confirmation email
     */
    public static boolean sendCheckInConfirmation(String recipientEmail, String studentName, 
                                                  String eventTitle) {
        String subject = "Check-in Confirmed: " + eventTitle;
        String body = "Dear " + studentName + ",\n\n" +
                     "Your attendance for the event has been confirmed:\n" +
                     "Event: " + eventTitle + "\n\n" +
                     "Thank you for attending!\n\n" +
                     "Best regards,\n" +
                     "Event Management Team";
        
        return sendEmail(recipientEmail, subject, body);
    }
}
