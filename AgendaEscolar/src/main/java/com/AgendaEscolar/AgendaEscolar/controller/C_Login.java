package com.AgendaEscolar.AgendaEscolar.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class C_Login {

    @GetMapping("/login")
    public String getCadastro(HttpSession session) {
        if(session.getAttribute("usuario") != null){
            return "index";
        }
        return "/login";
    }
}
