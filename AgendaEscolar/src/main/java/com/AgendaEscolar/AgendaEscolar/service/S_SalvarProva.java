package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.repository.R_SalvarProva;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class S_SalvarProva {
    private static R_SalvarProva r_salvarProva;

    public S_SalvarProva(R_SalvarProva r_salvarProva) {
        this.r_salvarProva = r_salvarProva;
    }

    // Método para salvar a prova
    public M_SalvarProva salvarProva(String titulo, String descricao, String data, String tipo, Long id) {
        M_SalvarProva prova = new M_SalvarProva();
        boolean podeSalvar = true;

        if (titulo == null || titulo.trim().isEmpty()) {
            podeSalvar = false;
        } else {
            prova.setTitulo(titulo);
        }

        prova.setDescricao(descricao);
        prova.setTipo(tipo);
        prova.setData(data);

        if (podeSalvar) {
            if (id != null) {
                Optional<M_SalvarProva> provaExistente = r_salvarProva.findById(id);
                if (provaExistente.isPresent()) {
                    M_SalvarProva provaParaAtualizar = provaExistente.get();
                    provaParaAtualizar.setTitulo(titulo);
                    provaParaAtualizar.setDescricao(descricao);
                    provaParaAtualizar.setTipo(tipo);
                    provaParaAtualizar.setData(data);
                    return r_salvarProva.save(provaParaAtualizar);
                }
            } else {
                prova.setDataCriacao(LocalDateTime.now());
                return r_salvarProva.save(prova);
            }
        }
        return null;
    }

    // Método para buscar todas as provas
    public List<M_SalvarProva> buscarTodasProvas() {
        return r_salvarProva.findAll();  // Chama o método do repositório para buscar todas as provas
    }

    public static boolean deletarProva(int id) {
        try {
            r_salvarProva.deleteById(Long.valueOf(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
