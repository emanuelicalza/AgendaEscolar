package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "materias")
public class M_Materias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private M_Usuarios professor;

    @ManyToOne
    private M_Turmas turma; // Relacionamento com turma

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public M_Usuarios getProfessor() {
        return professor;
    }

    public void setProfessor(M_Usuarios professor) {
        this.professor = professor;
    }

    public M_Turmas getTurma() {
        return turma;
    }

    public void setTurma(M_Turmas turma) {
        this.turma = turma;
    }
}
