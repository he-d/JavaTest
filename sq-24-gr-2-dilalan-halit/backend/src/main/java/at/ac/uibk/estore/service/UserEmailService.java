package at.ac.uibk.estore.service;

public interface UserEmailService {
    void sendEmail(String to, String subject, String text);
}
