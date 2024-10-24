package com.AgendaEscolar.AgendaEscolar.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class C_Login {
    @GetMapping("/modal/login")
    public String loginModal() {
        return "fragments/modal-login"; // Retorna a parte do modal em uma view Thymeleaf
    }

    @GetMapping("/modal/cadastro")
    public String cadastroModal() {
        return "fragments/modal-cadastro"; // Retorna a parte do modal em uma view Thymeleaf
    }
}
