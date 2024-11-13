package com.AgendaEscolar.AgendaEscolar.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class S_Email {
    private final JavaMailSender mailSender; // Removido static

    @Autowired
    public S_Email(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



    @Async
    public void enviaEmail(String to, String title, String message) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(title);
        email.setText(message);

        mailSender.send(email);

    }

    public String gerarSenha(int length) {
        // Caractere a ser usado na geração da senha
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            senha.append(chars.charAt(index));
        }

        return senha.toString();
    }
}