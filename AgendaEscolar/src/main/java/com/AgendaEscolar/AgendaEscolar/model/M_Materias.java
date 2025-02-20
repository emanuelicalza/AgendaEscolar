package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "materias")
public class M_Materias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_id")
    private M_Usuarios professor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "turma_id")
    private M_Turmas turma;

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

    // Método para combinar nome da matéria e turma
    public String getDescricaoComTurma() {
        return nome + " - " + (turma != null ? turma.getNomeFormatado() : "Sem Turma");
    }


}
