package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import com.AgendaEscolar.AgendaEscolar.model.M_Turmas;
import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.repository.R_Materia;
import com.AgendaEscolar.AgendaEscolar.repository.R_Turma;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario;
import com.AgendaEscolar.AgendaEscolar.service.S_Materia;
import com.AgendaEscolar.AgendaEscolar.service.S_Turma;
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
    private final S_Turma s_turma; // Serviço para listar turmas
    @Autowired
    private R_Turma r_turma; // Injeção do repositório de Turma

    @Autowired
    private R_Usuario r_usuario; // Injeção do repositório de Usuário (ou Professor)

    @Autowired
    private R_Materia r_materia; // Injeção do repositório de Matéria

    @Autowired
    public C_Materia(S_Materia s_materia, S_Usuario s_usuario, S_Turma s_turma) {
        this.s_materia = s_materia;
        this.s_usuario = s_usuario;
        this.s_turma = s_turma;
    }

    // Método para exibir a página de gerenciamento de matérias
    @GetMapping("/gerirMaterias")
    public String gerirMaterias(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/";
        }

        List<M_Materias> materias = s_materia.listarMaterias();
        List<M_Usuarios> professores = s_usuario.listarProfessores();
        List<M_Turmas> turmas = s_turma.listarTurmas(); // Lista as turmas

        model.addAttribute("materias", materias);
        model.addAttribute("professores", professores);
        model.addAttribute("turmas", turmas); // Adiciona as turmas ao modelo
        model.addAttribute("usuario", usuario);

        return "gerirMaterias";
    }

    // Método para salvar a nova matéria
    @PostMapping("/gerirMaterias/criarMateria")
    public String criarMateria(
            @RequestParam("nome") String nome,
            @RequestParam("professor.id") Long professorId,
            @RequestParam("turma.id") Long turmaId) {

        // Buscando a turma e o professor usando o repositório injetado
        M_Turmas turma = r_turma.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        M_Usuarios professor = r_usuario.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Cria a nova matéria
        M_Materias materia = new M_Materias();
        materia.setNome(nome);
        materia.setTurma(turma);
        materia.setProfessor(professor);

        // Salva a matéria no banco
        r_materia.save(materia);

        return "redirect:/gerirMaterias"; // Redireciona para a página de gestão de matérias
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
            return "redirect:/";
        }

        s_materia.atualizarMateria(materia); // Agora atualiza o campo 'turma'
        return "redirect:/gerirMaterias";
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

    @GetMapping("/selecionarMateria")
    public String selecionarMateria(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() < 2) { // Apenas professores e diretores
            return "redirect:/";
        }

        List<M_Materias> materias = s_materia.listarMaterias();
        model.addAttribute("materias", materias);
        return "selecionarMateria";
    }

}
