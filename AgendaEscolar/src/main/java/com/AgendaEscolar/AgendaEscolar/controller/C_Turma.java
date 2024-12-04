package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Turmas;
import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Turma;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class C_Turma {

    private final S_Turma s_turma;
    private final S_Usuario s_usuario;

    @Autowired
    public C_Turma(S_Turma s_turma, S_Usuario s_usuario) {
        this.s_turma = s_turma;
        this.s_usuario = s_usuario;
    }

    // Exibir a página de gerenciamento de turmas
    @GetMapping("/gerirTurmas")
    public String gerirTurmas(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        List<M_Turmas> turmas = s_turma.listarTurmas();
        model.addAttribute("turmas", turmas);  // Adiciona a lista de turmas ao modelo
        model.addAttribute("usuario", usuario); // Adiciona o usuário ao modelo
        return "gerirTurmas"; // Página de gerenciamento de turmas
    }

    // Criar uma nova turma
    @PostMapping("/gerirTurmas/criarTurma")
    public String criarTurma(@ModelAttribute M_Turmas turma, @SessionAttribute(name = "usuario") M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        s_turma.salvarTurma(turma); // Salva a turma
        return "redirect:/gerirTurmas"; // Redireciona para a página de turmas
    }

    // Editar turma
    @GetMapping("/gerirTurmas/editarTurma")
    public String editarTurma(@RequestParam("id") Long id, @SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }

        M_Turmas turma = s_turma.buscarTurmaPorId(id);
        if (turma != null) {
            model.addAttribute("turma", turma); // Adiciona a turma ao modelo
            return "editarTurma"; // Página para editar a turma
        } else {
            return "redirect:/gerirTurmas"; // Redireciona se não encontrar a turma
        }
    }

    // Atualizar turma
    @PostMapping("/gerirTurmas/atualizarTurma")
    @ResponseBody  // Retorna resposta simples
    public String atualizarTurma(@ModelAttribute M_Turmas turma, @SessionAttribute(name = "usuario") M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "erro"; // Retorna erro se não for diretor
        }

        s_turma.atualizarTurma(turma); // Atualiza a turma
        return "sucesso"; // Retorna sucesso
    }


    // Excluir turma
    @PostMapping("/gerirTurmas/excluirTurma")
    @ResponseBody
    public String excluirTurma(@RequestParam("id") Long id, @SessionAttribute(name = "usuario") M_Usuarios usuario) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "erro";
        }

        try {
            s_turma.excluirTurma(id);
            return "sucesso";
        } catch (Exception e) {
            return "erro: " + e.getMessage();
        }
    }


    @GetMapping("/turmas/{id}")
    @ResponseBody
    public ResponseEntity<?> acessarTurmaAjax(@PathVariable Long id) {
        M_Turmas turma = s_turma.buscarTurmaPorId(id);
        if (turma == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada.");
        }

        return ResponseEntity.ok("Turma encontrada.");
    }

}