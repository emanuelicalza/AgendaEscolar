package com.AgendaEscolar.AgendaEscolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_SalvarProva extends JpaRepository<com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva,Long> {
    }

