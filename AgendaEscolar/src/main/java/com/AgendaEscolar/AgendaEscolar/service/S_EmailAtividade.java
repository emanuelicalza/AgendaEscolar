package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.repository.R_EmailAtividade;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_EmailAtividade {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private R_EmailAtividade rEmailAtividade; // Usando o repositório correto
    public List<String> buscarEmailsUsuariosTipo1() {
        List<String> emails = rEmailAtividade.findEmailsUsuariosTipo1();
        return emails;
    }

    public void enviarEmailParaUsuariosTipo1(String materiaSelecionada) {
        List<String> emails = buscarEmailsUsuariosTipo1();

        // Criar o objeto MimeMessage
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("tccagendaescolar@gmail.com"); // E-mail remetente
            helper.setSubject("Nova Atividade Criada");

            // Corpo do e-mail, incluindo a matéria
            String corpoEmail = "Uma nova atividade foi criada para a matéria: " + materiaSelecionada;
            helper.setText(corpoEmail);

            // Enviar o e-mail para todos os usuários tipo 1
            for (String email : emails) {
                helper.setTo(email);
                javaMailSender.send(message);
            }

            System.out.println("E-mails enviados com sucesso!");
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }
}
