package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.service.S_EmailAtividade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emailatividade")
public class C_EmailAtividade {

    @Autowired
    private S_EmailAtividade sEmailAtividade;

    @PostMapping("/enviar")
    public String enviarEmail(@RequestBody String json) {

        // Enviar o e-mail após a criação da atividade
        sEmailAtividade.enviarEmailParaUsuariosTipo1(json);

        return "E-mails enviados com sucesso!";
    }
}

