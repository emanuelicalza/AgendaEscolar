package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class C_Login {

    @Autowired
    private R_Usuario usuarioRepository;

    @GetMapping("/login")
    public String getLogin(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/"; // Se o usuário já estiver logado, redireciona para a página principal
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Optional<M_Usuarios> usuarioOpt = usuarioRepository.findByEmail(email);

        model.addAttribute("eNois", "podcrê");


        if (usuarioOpt.isPresent()) {
            M_Usuarios usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(password)) {
                session.setAttribute("usuario", usuario);
                return "redirect:/"; // Redireciona para a página principal
            }
        }

        model.addAttribute("error", "Email ou senha inválidos");
        return "login"; // Volta para a página de login com a mensagem de erro
    }
}
