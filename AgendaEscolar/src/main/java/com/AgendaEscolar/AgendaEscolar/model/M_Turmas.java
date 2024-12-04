package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<M_Materias> materias;

    // Getters e Setters
    public List<M_Materias> getMaterias() {
        return materias;
    }

    public void setMaterias(List<M_Materias> materias) {
        this.materias = materias;
    }

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

    // Método personalizado para formatar o nome da turma
    public String getNomeFormatado() {
        return serie + tipo + " " + (nivel.startsWith("F") ? "F" : "M");
    }
}
