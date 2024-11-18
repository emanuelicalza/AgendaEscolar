package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import com.AgendaEscolar.AgendaEscolar.repository.R_Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class S_Materia {

    @Autowired
    private R_Materia r_materia;

    // Salva uma nova matéria
    public M_Materias salvarMateria(M_Materias materia) {
        return r_materia.save(materia);
    }

    // Lista todas as matérias
    public List<M_Materias> listarMaterias() {
        return r_materia.findAll();
    }

    // Buscar uma matéria pelo ID
    public M_Materias buscarMateriaPorId(Long id) {
        Optional<M_Materias> materia = r_materia.findById(id);
        return materia.orElse(null); // Retorna a matéria ou null se não encontrar
    }

    // Atualizar uma matéria existente
    public M_Materias atualizarMateria(M_Materias materia) {
        // Verifica se a matéria existe antes de tentar atualizar
        if (r_materia.existsById(materia.getId())) {
            return r_materia.save(materia); // Atualiza a matéria no banco de dados
        }
        return null; // Retorna null caso a matéria não exista
    }

    // Método para excluir uma matéria
    public void excluirMateria(Long id) {
        if (!r_materia.existsById(id)) {
            throw new IllegalArgumentException("Matéria não encontrada para exclusão."); // Lança uma exceção específica
        }
        r_materia.deleteById(id); // Exclui a matéria pelo ID
    }

    public List<M_Materias> listarMateriasPorProfessor(Long professorId) {
        return r_materia.findByProfessorId(professorId);
    }


}
