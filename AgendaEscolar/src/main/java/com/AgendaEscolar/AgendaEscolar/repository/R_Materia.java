package com.AgendaEscolar.AgendaEscolar.repository;


import com.AgendaEscolar.AgendaEscolar.model.M_Materias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Materia extends JpaRepository<M_Materias, Long> {
}