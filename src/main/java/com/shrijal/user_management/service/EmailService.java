package com.shrijal.user_management.service;


import com.shrijal.user_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String HR_EMAIL = "hr@techcorp.com";

    public void sendUserCreatedEmail(User user) {
        sendPlainEmail(
                user.getEmail(),
                "Welcome to TechCorp - Account Created",
                "Hi " + user.getFirstName() + ",\n\n" +
                        "Your account has been successfully created at TechCorp.\n" +
                        "Department: " + user.getDepartment() + "\n" +
                        "Role: " + user.getRole() + "\n\n" +
                        "Regards,\nTechCorp HR Team"
        );

        sendPlainEmail(
                HR_EMAIL,
                "New User Created: " + user.getFirstName(),
                "A new user has been created.\n\n" +
                        "Name: " + user.getFirstName() + " " + user.getLastName() + "\n" +
                        "Email: " + user.getEmail()
        );
    }

    public void sendUserUpdatedEmail(User user) {
        sendPlainEmail(
                user.getEmail(),
                "Account Updated",
                "Hi " + user.getFirstName() + ",\n\n" +
                        "Your account details have been updated."
        );

        sendPlainEmail(
                HR_EMAIL,
                "User Updated: " + user.getFirstName(),
                "User details were updated for: " + user.getEmail()
        );
    }

    public void sendUserDeletedEmail(User user) {
        sendPlainEmail(
                HR_EMAIL,
                "Account Deleted - " + user.getFirstName(),
                "User " + user.getEmail() + " has been deactivated."
        );
    }

    private void sendPlainEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
