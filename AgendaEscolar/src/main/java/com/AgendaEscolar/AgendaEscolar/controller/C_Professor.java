package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Email;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class C_Professor {
    private final S_Usuario s_usuario;
    private final S_Email s_email;

    public C_Professor(S_Usuario s_usuario, S_Email s_email) {
        this.s_usuario = s_usuario;
        this.s_email = s_email;
    }

    // Método para exibir o formulário de criação do professor
    @GetMapping("/criarProfessor")
    public String mostrarFormularioCriacaoProfessor(@SessionAttribute(name = "usuario", required = false) M_Usuarios usuario, Model model) {
        if (usuario == null || usuario.getTipo() != 3) {
            return "redirect:/"; // Redireciona para a home se não for um diretor
        }
        return "criarProfessor"; // Página do formulário para criar professor
    }

    // Método para processar o formulário de criação ou atualização do professor
    @PostMapping("/criarProfessor")
    public String salvarProfessor(
            @RequestParam(required = false) Long id, // Adiciona um parâmetro opcional para ID
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String dataNascimento,
            RedirectAttributes redirectAttributes) {

        LocalDate dataNasc = LocalDate.parse(dataNascimento); // Lê a data corretamente

        if (id != null) { // Se o ID estiver presente, atualiza o professor
            s_usuario.atualizarProfessor(id, nome, email, dataNasc); // Método para atualizar professor
            redirectAttributes.addFlashAttribute("success", "Professor atualizado com sucesso.");
        } else { // Se o ID não estiver presente, cria um novo professor
            String senha = gerarSenha(); // Gera uma senha aleatória
            s_usuario.cadastrarUsuario(nome, email, senha, senha, dataNasc, 2); // Passa tipo 2 para professor

            // Envio de email
            String tituloEmail = "Bem-vindo ao sistema!";
            String mensagemEmail = "Sua senha é: " + senha;
            s_email.enviaEmail(email, tituloEmail, mensagemEmail);

            redirectAttributes.addFlashAttribute("success", "Professor criado com sucesso.");
        }

        return "redirect:/areaDiretor"; // Redireciona para a página da área do diretor
    }

    private String gerarSenha() {
        return UUID.randomUUID().toString().substring(0, 8); // Gera uma senha de 8 caracteres
    }

    @GetMapping("/editarProfessor/{id}")
    public String editarProfessor(@PathVariable Long id, Model model) {
        M_Usuarios professor = s_usuario.obterProfessorPorId(id); // Método para buscar professor pelo ID
        if (professor == null) {
            // Se o professor não for encontrado, redirecione ou lance um erro apropriado
            return "redirect:/areaDiretor"; // Ajuste conforme necessário
        }
        model.addAttribute("professor", professor); // Adiciona o professor ao modelo
        return "criarProfessor"; // Redireciona para a página de criarProfessor
    }
}
