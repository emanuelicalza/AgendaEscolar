package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_Turmas;
import com.AgendaEscolar.AgendaEscolar.repository.R_Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class S_Turma {

    @Autowired
    private R_Turma r_turma;

    // Salvar uma nova turma
    public M_Turmas salvarTurma(M_Turmas turma) {
        return r_turma.save(turma);
    }

    // Listar todas as turmas
    public List<M_Turmas> listarTurmas() {
        return r_turma.findAll();
    }

    // Buscar uma turma por ID
    public M_Turmas buscarTurmaPorId(Long id) {
        Optional<M_Turmas> turma = r_turma.findById(id);
        return turma.orElse(null);
    }

    // Atualizar uma turma
    public M_Turmas atualizarTurma(M_Turmas turma) {
        if (r_turma.existsById(turma.getId())) {
            return r_turma.save(turma);
        }
        return null;
    }

    // Excluir uma turma
    public void excluirTurma(Long id) {
        if (r_turma.existsById(id)) {
            r_turma.deleteById(id);
        } else {
            throw new IllegalArgumentException("Turma não encontrada.");
        }
    }
}
