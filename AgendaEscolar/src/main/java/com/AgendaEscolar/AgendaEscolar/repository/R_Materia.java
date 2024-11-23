package com.AgendaEscolar.AgendaEscolar.repository;


import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Materia extends JpaRepository<M_Materias, Long> {
    // Busca as matérias associadas ao professor com a turma carregada
    @Query("SELECT m FROM M_Materias m LEFT JOIN FETCH m.turma WHERE m.professor.id = :professorId")
    List<M_Materias> findByProfessorId(@Param("professorId") Long professorId);

}