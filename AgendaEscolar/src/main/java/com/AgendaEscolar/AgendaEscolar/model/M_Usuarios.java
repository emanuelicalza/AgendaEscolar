package com.AgendaEscolar.AgendaEscolar.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "usuarios")
public class M_Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private Integer tipo; // Usando Integer para representar o tipo

    private LocalDate dataNascimento; // Usando LocalDate para data de nascimento

    private Long idDiretor; // Referência ao ID do diretor, se necessário

    // Método para formatar a data de nascimento
    public String getDataNascimentoFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataNascimento != null ? this.dataNascimento.format(formatter) : "";
    }

    // Construtores
    public M_Usuarios() {
    }

    public M_Usuarios(Long id, String nome, String email, String senha, Integer tipo, LocalDate nasc, Long idDiretor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.dataNascimento = nasc; // Correção aqui
        this.idDiretor = idDiretor;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate nasc) {
        this.dataNascimento = nasc;
    }

    public Long getIdDiretor() {
        return idDiretor;
    }

    public void setIdDiretor(Long idDiretor) {
        this.idDiretor = idDiretor;
    }
}
