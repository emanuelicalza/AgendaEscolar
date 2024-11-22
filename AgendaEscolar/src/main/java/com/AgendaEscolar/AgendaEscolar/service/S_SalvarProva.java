package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.repository.R_SalvarProva;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class S_SalvarProva {

    private final R_SalvarProva r_salvarProva;

    public S_SalvarProva(R_SalvarProva r_salvarProva) {
        this.r_salvarProva = r_salvarProva;
    }

    // Método para salvar ou atualizar a prova
    public M_SalvarProva salvarProva(String titulo, String descricao, String data, String tipo, Long id) {
        M_SalvarProva prova = new M_SalvarProva();
        boolean podeSalvar = true;

        if (titulo == null || titulo.trim().isEmpty()) {
            podeSalvar = false; // Validação básica do título
        } else {
            prova.setTitulo(titulo);
        }

        prova.setDescricao(descricao);
        prova.setTipo(tipo);
        prova.setData(data);  // Verificar se o formato de data está correto

        if (podeSalvar) {
            if (id != null) {
                Optional<M_SalvarProva> provaExistente = r_salvarProva.findById(id);
                if (provaExistente.isPresent()) {
                    M_SalvarProva provaParaAtualizar = provaExistente.get();
                    provaParaAtualizar.setTitulo(titulo);
                    provaParaAtualizar.setDescricao(descricao);
                    provaParaAtualizar.setTipo(tipo);
                    provaParaAtualizar.setData(data);
                    return r_salvarProva.save(provaParaAtualizar);  // Atualiza prova existente
                }
            } else {
                prova.setDataCriacao(LocalDateTime.now());  // Adiciona data de criação para nova prova
                return r_salvarProva.save(prova);  // Salva nova prova
            }
        }
        return null;
    }

    // Método para atualizar uma prova (refatorado)
    public boolean atualizarProva(Long id, String titulo, String descricao, String data, String tipo) {
        Optional<M_SalvarProva> provaExistente = r_salvarProva.findById(id);
        if (provaExistente.isPresent()) {
            M_SalvarProva provaParaAtualizar = provaExistente.get();
            provaParaAtualizar.setTitulo(titulo);
            provaParaAtualizar.setDescricao(descricao);
            provaParaAtualizar.setTipo(tipo);
            provaParaAtualizar.setData(data);
            r_salvarProva.save(provaParaAtualizar);  // Atualiza a prova existente
            return true;
        }
        return false;  // Retorna false se a prova não for encontrada
    }

    // Método para buscar todas as provas
    public List<M_SalvarProva> buscarTodasProvas() {
        return r_salvarProva.findAll();  // Retorna todas as provas do banco de dados
    }

    // Método para deletar uma prova pelo ID
    public void deletarProva(Long id) throws Exception {
        Optional<M_SalvarProva> prova = r_salvarProva.findById(id);
        if (prova.isPresent()) {
            r_salvarProva.deleteById(id);  // Deleta prova se ela existir
        } else {
            throw new Exception("Prova não encontrada");  // Lança exceção se não encontrar
        }
    }
}
