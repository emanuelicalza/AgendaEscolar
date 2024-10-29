package com.AgendaEscolar.AgendaEscolar.repository;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuarios, Long> {
}
