package com.college.eventclub;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * EmailUtil - Utility class for sending emails
 */
public class EmailUtil {

    private static final String SENDER_EMAIL = "your-email@gmail.com";
    private static final String SENDER_PASSWORD = "your-app-password";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    /**
     * Send email notification
     */
    public static boolean sendEmail(String recipientEmail, String subject, String body) {
        try {
            // Setup properties
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");

            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });

            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
            return true;

        } catch (MessagingException e) {
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
