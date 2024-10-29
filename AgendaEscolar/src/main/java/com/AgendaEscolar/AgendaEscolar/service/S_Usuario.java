package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class S_Usuario {
    @Autowired
    private R_Usuario r_usuario;


    public M_Usuarios cadastrarUsuario(String nome, String email, String senha, String confirmarSenha, Integer tipo, LocalDate dataNascimento, Long idDiretor) {
        // Verifica se as senhas coincidem
        if (!senha.equals(confirmarSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }


        // Cria uma nova instância de M_Usuario
        M_Usuarios novoUsuario = new M_Usuarios();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha); // A senha será hashada em seguida
        novoUsuario.setTipo(1); // Tipo padrão
        novoUsuario.setDataNascimento(dataNascimento);
        novoUsuario.setIdDiretor(null);

        // Salva o usuário no banco de dados
        return r_usuario.save(novoUsuario);
    }

    public void cadastrarUsuario(M_Usuarios novoUsuario) {
    }
}
