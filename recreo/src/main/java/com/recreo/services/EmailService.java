package com.recreo.services;

import com.recreo.exceptions.RecreoApiException;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendTemporaryPassword(String destinatario, String temporaryPassword) throws RecreoApiException, MessagingException;
}
