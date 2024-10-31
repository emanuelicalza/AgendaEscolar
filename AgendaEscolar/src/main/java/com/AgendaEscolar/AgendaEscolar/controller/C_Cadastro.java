package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class C_Cadastro {
    private final S_Usuario s_usuario;

    public C_Cadastro(S_Usuario s_usuario) {
        this.s_usuario = s_usuario;
    }

    @GetMapping("/cadastro")
    public String getCadastro() {
        return "/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            @RequestParam String dataNascimento,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não coincidem.");
            return "redirect:/cadastro";
        }

        LocalDate dataNasc = LocalDate.parse(dataNascimento);
        M_Usuarios usuario = s_usuario.cadastrarUsuario(nome, email, senha, confirmarSenha, dataNasc);

        // Armazena o usuário na sessão
        session.setAttribute("usuario", usuario);

        redirectAttributes.addFlashAttribute("success", "Usuário cadastrado com sucesso.");
        return "redirect:/"; // Altere para o mapeamento correto do seu index
    }
}
