package com.AgendaEscolar.AgendaEscolar.repository;

import com.AgendaEscolar.AgendaEscolar.model.M_EmailAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface R_EmailAtividade extends JpaRepository<M_EmailAtividade, Long> {

    // Método para buscar os e-mails dos usuários tipo 1 na tabela 'provas'
    @Query(value = "SELECT email FROM usuarios m WHERE tipo = 1",nativeQuery = true)
    List<String> findEmailsUsuariosTipo1();
}
