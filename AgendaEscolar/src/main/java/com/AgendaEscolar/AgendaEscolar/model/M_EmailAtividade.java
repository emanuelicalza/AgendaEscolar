package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class M_EmailAtividade {
    @Id
    private Long id;
    private String email;
    private int tipo;


    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}

