package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;

import java.util.List;


@Controller
public class C_Diretor {
    private final S_Usuario s_usuario;


    public C_Diretor(S_Usuario s_usuario) {
        this.s_usuario = s_usuario;
    }

    // Método para exibir a área do diretor e listar professores cadastrados
    @GetMapping("/areaDiretor")
    public String areaDiretor(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        model.addAttribute("usuario", usuario);

        List<M_Usuarios> professores = s_usuario.obterProfessores();
        System.out.println("Professores encontrados: " + professores); // Log para ver se existem professores

        model.addAttribute("professores", professores); // Adiciona a lista de professores ao modelo
        return "areaDiretor"; // Página da área do diretor
    }


}
