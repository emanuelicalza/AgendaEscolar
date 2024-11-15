package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Materia;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class C_Materias {

    private final S_Materia s_materia;
    private final S_Usuario s_usuario;

    @Autowired
    public C_Materias(S_Materia s_materia, S_Usuario s_usuario) {
        this.s_materia = s_materia;
        this.s_usuario = s_usuario;
    }

    // Método para exibir a página de gerenciamento de matérias
    @GetMapping("/gerirMaterias")
    public String gerirMaterias(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        // Carregar as matérias e professores para exibir na página
        List<M_Materias> materias = s_materia.listarMaterias();
        List<M_Usuarios> professores = s_usuario.listarProfessores();

        model.addAttribute("materias", materias); // Adiciona a lista de matérias ao modelo
        model.addAttribute("professores", professores); // Adiciona a lista de professores ao modelo
        model.addAttribute("usuario", usuario); // Adiciona os dados do usuário logado
        return "gerirMaterias"; // Página de gerenciamento de matérias
    }

    // Método para salvar a nova matéria
    @PostMapping("/gerirMaterias/criarMateria")
    public String criarMateria(@ModelAttribute M_Materias materia, @SessionAttribute(name = "usuario") M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        // Salvar a nova matéria
        s_materia.salvarMateria(materia);
        return "redirect:/gerirMaterias"; // Redireciona para a página de gerenciamento de matérias
    }
}
