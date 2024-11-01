package com.AgendaEscolar.AgendaEscolar.service;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class S_Usuario {
    @Autowired
    private R_Usuario r_usuario;

    public M_Usuarios cadastrarUsuario(String nome, String email, String senha, String confirmarSenha, LocalDate dataNascimento,  int tipo) {
        // Verifica se as senhas coincidem
        if (!senha.equals(confirmarSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        // Cria uma nova instância de M_Usuarios
        M_Usuarios usuario = new M_Usuarios();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha); // Armazena a senha como está
        usuario.setDataNascimento(dataNascimento);
        usuario.setTipo(tipo); // Define o tipo do usuário (1 para aluno)

        // Salve o usuário no repositório
        return r_usuario.save(usuario);
    }

    public List<M_Usuarios> obterProfessores() {
        List<M_Usuarios> professores = r_usuario.findByTipo(2);
        System.out.println("Professores encontrados: " + professores); // Log para verificar
        return professores;
    }

    // Método para obter professor por ID
    public M_Usuarios obterProfessorPorId(Long id) {
        Optional<M_Usuarios> optionalProfessor = r_usuario.findById(id);
        return optionalProfessor.orElse(null); // Retorna o professor se encontrado, caso contrário, null
    }

    // Método para atualizar professor
    public void atualizarProfessor(Long id, String nome, String email, LocalDate dataNascimento) {
        M_Usuarios professor = obterProfessorPorId(id);
        if (professor != null) {
            professor.setNome(nome);
            professor.setEmail(email);
            professor.setDataNascimento(dataNascimento);
            r_usuario.save(professor); // Salva as alterações no repositório
        }
    }

}
