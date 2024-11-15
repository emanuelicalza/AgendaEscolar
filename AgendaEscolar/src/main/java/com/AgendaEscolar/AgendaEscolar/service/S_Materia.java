package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.repository.R_Materia;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Materia {

    @Autowired
    private R_Materia r_materia;

    @Autowired
    private R_Usuario r_usuario;

    // Salva uma nova matéria
    public M_Materias salvarMateria(M_Materias materia) {
        return r_materia.save(materia);
    }

    // Lista todas as matérias
    public List<M_Materias> listarMaterias() {
        return r_materia.findAll();
    }
}

