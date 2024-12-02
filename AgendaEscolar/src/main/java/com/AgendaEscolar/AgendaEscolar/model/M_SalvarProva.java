package com.AgendaEscolar.AgendaEscolar.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "provas")
public class M_SalvarProva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private String tipo;

    private String data; // Você pode considerar usar LocalDate ou LocalDateTime para melhor gerenciamento de datas.

    private LocalDateTime dataCriacao;

    // Construtores
    public M_SalvarProva() {
        this.dataCriacao = LocalDateTime.now(); // Inicializa a data de criação
    }

    public M_SalvarProva(Long id, String titulo, String descricao, String tipo, String data) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.data = data;
        this.dataCriacao = LocalDateTime.now(); // Inicializa a data de criação
    }

    public M_SalvarProva(boolean b, String atvdFoiDeArrasta) {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }



        @ManyToOne
        @JoinColumn(name = "materia_id")
        private M_Materias materia;

        @ManyToOne
        @JoinColumn(name = "usuario_id")
        private M_Usuarios usuario;

        // Getters e setters
    }
}
