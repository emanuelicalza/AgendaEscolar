package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.repository.R_SalvarProva;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class S_SalvarProva {
    private final R_SalvarProva r_salvarProva;

    public S_SalvarProva(R_SalvarProva r_salvarProva) {
        this.r_salvarProva = r_salvarProva;
    }

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
}
