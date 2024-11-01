package com.AgendaEscolar.AgendaEscolar.repository;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuarios, Long> {
    Optional<M_Usuarios> findByEmail(String email); // Adiciona busca por email
    List<M_Usuarios> findByTipo(int tipo); // Método para encontrar usuários pelo tipo

}
