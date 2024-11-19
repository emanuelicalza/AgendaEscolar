package com.AgendaEscolar.AgendaEscolar.repository;


import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Materia extends JpaRepository<M_Materias, Long> {
    List<M_Materias> findByProfessorId(Long professorId);

}