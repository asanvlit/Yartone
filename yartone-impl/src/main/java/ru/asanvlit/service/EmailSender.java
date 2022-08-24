package ru.asanvlit.service;

public interface EmailSender {

    void sendEmail(String to, String subject, String emailText);
}
