package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Email;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class C_Diretor {
    private final S_Usuario s_usuario;
    private final S_Email s_email;

    public C_Diretor(S_Usuario s_usuario, S_Email s_email) {
        this.s_usuario = s_usuario;
        this.s_email = s_email;
    }

    // Método para exibir a área do diretor
    @GetMapping("/areaDiretor")
    public String areaDiretor(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }
        model.addAttribute("usuario", usuario);
        return "areaDiretor"; // Página da área do diretor
    }

    // Método para exibir o formulário de criação do professor
    @GetMapping("/criarProfessor")
    public String mostrarFormularioCriacaoProfessor(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }
        return "criarProfessor"; // Página do formulário para criar professor
    }

    // Método para processar o formulário de criação do professor
    @PostMapping("/criarProfessor")
    public String criarProfessor(@RequestParam String nome, @RequestParam String email) {
        String senha = gerarSenha(); // Gera uma senha aleatória
        s_usuario.cadastrarUsuario(nome, email, senha, senha, LocalDate.now());

        String tituloEmail = "Bem-vindo ao sistema!";
        String mensagemEmail = "Sua senha é: " + senha;
        s_email.enviaEmail(email, tituloEmail, mensagemEmail);

        return "redirect:/areaDiretor"; // Redireciona para a página da área do diretor
    }

    private String gerarSenha() {
        return UUID.randomUUID().toString().substring(0, 8); // Gera uma senha de 8 caracteres
    }
}
