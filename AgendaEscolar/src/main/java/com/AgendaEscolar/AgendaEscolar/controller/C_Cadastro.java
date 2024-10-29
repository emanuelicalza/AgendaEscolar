package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class C_Cadastro {


    private final S_Usuario s_usuario;

    // Injeção do serviço pelo construtor
    public C_Cadastro(S_Usuario s_usuario) {
        this.s_usuario = s_usuario; // Atribuindo a instância do serviço
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            @RequestParam String dataNascimento) {

        // Verifica se a senha e a confirmação da senha são iguais
        if (!senha.equals(confirmarSenha)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("As senhas não coincidem.");
        }

        // Lógica para salvar o usuário no banco de dados
        M_Usuarios novoUsuario = new M_Usuarios();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);
        novoUsuario.setDataNascimento(LocalDate.parse(dataNascimento)); // Conversão da data
        novoUsuario.setTipo(1); // Tipo fixo como 1
        novoUsuario.setIdDiretor(null); // idDiretor fixo como null

        s_usuario.cadastrarUsuario(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso.");
    }
}
