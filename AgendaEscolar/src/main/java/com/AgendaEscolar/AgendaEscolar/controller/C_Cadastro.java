package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import com.AgendaEscolar.AgendaEscolar.repository.R_Usuario; // Importe o repositório para consulta ao banco
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class C_Cadastro {
    private final S_Usuario s_usuario;
    private final R_Usuario usuarioRepository;  // Adicionando o repositório para consultar o banco de dados

    public C_Cadastro(S_Usuario s_usuario, R_Usuario usuarioRepository) {
        this.s_usuario = s_usuario;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/cadastro")
    public String getCadastro() {
        return "cadastro"; // Certifique-se de que o nome da view esteja correto
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            @RequestParam String dataNascimento,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // Verifica se já existe um usuário com o email informado
        Optional<M_Usuarios> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("errorEmail", "Já existe um usuário cadastrado com esse email.");
            return "redirect:/cadastro";
        }

        // Verificação das senhas
        if (!senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("errorSenha", "As senhas não coincidem.");
            return "redirect:/cadastro";
        }

        // Lógica do cadastro
        LocalDate dataNasc = LocalDate.parse(dataNascimento);
        M_Usuarios usuario = s_usuario.cadastrarUsuario(nome, email, senha, senha, dataNasc, 1); // 1 para aluno

        // Armazena o usuário na sessão
        session.setAttribute("usuario", usuario);

        redirectAttributes.addFlashAttribute("success", "Usuário cadastrado com sucesso.");
        return "redirect:/"; // Redireciona para a página inicial
    }
}
