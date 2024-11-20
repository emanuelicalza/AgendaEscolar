package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "turmas")
public class M_Turmas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;  // Exemplo: 1º Ano, 2º Ano, etc.
    private String tipo;   // Exemplo: A, B, C
    private int ano;       // Exemplo: 2024
    private String nivel;  // Exemplo: Fundamental ou Médio

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
