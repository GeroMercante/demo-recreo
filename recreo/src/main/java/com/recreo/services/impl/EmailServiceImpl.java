package com.recreo.services.impl;

import com.recreo.exceptions.RecreoApiException;
import com.recreo.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendTemporaryPassword(String destinatario, String temporaryPassword) throws RecreoApiException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setFrom("develop.recreo@gmail.com");
        helper.setSubject("Contraseña temporal");

        StringBuilder body = new StringBuilder();
        body.append("Esta es su clave temporal");
        body.append("\n\n");
        body.append(temporaryPassword);
        body.append("\n\n");
        helper.setText(body.toString());

        javaMailSender.send(message);
    }
}
