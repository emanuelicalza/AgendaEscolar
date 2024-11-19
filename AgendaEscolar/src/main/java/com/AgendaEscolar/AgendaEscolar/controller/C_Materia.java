package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Materia;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class C_Materia {

    private final S_Materia s_materia;
    private final S_Usuario s_usuario;

    @Autowired
    public C_Materia(S_Materia s_materia, S_Usuario s_usuario) {
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

    // Método para exibir o formulário de edição de matéria
    @GetMapping("/gerirMaterias/editarMateria")
    public String editarMateria(@RequestParam("id") Long id, @SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        // Carregar a matéria pelo ID
        M_Materias materia = s_materia.buscarMateriaPorId(id);
        if (materia != null) {
            List<M_Usuarios> professores = s_usuario.listarProfessores();
            model.addAttribute("materia", materia); // Adiciona a matéria ao modelo
            model.addAttribute("professores", professores); // Adiciona a lista de professores ao modelo
            model.addAttribute("usuario", usuario); // Adiciona os dados do usuário logado
            return "editarMateria"; // Página de edição de matéria
        } else {
            return "redirect:/gerirMaterias"; // Se não encontrar a matéria, redireciona de volta
        }
    }

    // Método para atualizar a matéria
    @PostMapping("/gerirMaterias/atualizarMateria")
    public String atualizarMateria(@ModelAttribute M_Materias materia, @SessionAttribute(name = "usuario") M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        // Atualiza a matéria
        s_materia.atualizarMateria(materia);
        return "redirect:/gerirMaterias"; // Redireciona para a página de gerenciamento de matérias
    }

    @PostMapping("/gerirMaterias/excluirMateria")
    @ResponseBody
    public String excluirMateria(@RequestParam("id") Long id, @SessionAttribute(name = "usuario") M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        // Chama o serviço para excluir a matéria
        s_materia.excluirMateria(id);
        return "sucesso"; // Retorna sucesso
    }

    @GetMapping("/obterMateriasUsuario")
    @ResponseBody
    public List<M_Materias> obterMateriasUsuario(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario) {
        if (usuario == null) {
            return new ArrayList<>(); // Retorna lista vazia se não houver usuário logado
        }
        System.out.println("Usuário logado: " + usuario.getNome());

        if (usuario.getTipo() == 3) {
            return s_materia.listarMaterias(); // Diretor vê todas as matérias
        } else if (usuario.getTipo() == 2) {
            return s_materia.listarMateriasPorProfessor(usuario.getId()); // Professor vê suas matérias
        }

        return new ArrayList<>(); // Retorna lista vazia para outros casos
    }

}
