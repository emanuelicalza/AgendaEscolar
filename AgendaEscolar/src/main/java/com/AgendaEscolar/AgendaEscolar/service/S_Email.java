package com.AgendaEscolar.AgendaEscolar.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class S_Email {
    private final JavaMailSender mailSender; // Removido static

    @Autowired
    public S_Email(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviaEmail(String to, String title, String message) {
        // Cria uma nova mensagem de e-mail
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to); // Define o destinatário
        email.setSubject(title); // Define o assunto
        email.setText(message); // Define o conteúdo do e-mail

        // Envia a mensagem utilizando o mailSender
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