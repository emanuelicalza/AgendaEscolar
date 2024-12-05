package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_Usuarios;
import com.AgendaEscolar.AgendaEscolar.service.S_Email;
import com.AgendaEscolar.AgendaEscolar.service.S_Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import java.time.format.DateTimeFormatter;




@Controller
@RequestMapping("/professores") // Agrupando endpoints relacionados
public class C_Professor {

    private final S_Usuario s_usuario;
    private final S_Email s_email;

    public C_Professor(S_Usuario s_usuario, S_Email s_email) {
        this.s_usuario = s_usuario;
        this.s_email = s_email;
    }

    // Endpoint para criar ou atualizar professor via AJAX
    @PostMapping("/salvar")
    @ResponseBody
    public ResponseEntity<M_Usuarios> salvarProfessor(
            @RequestParam(required = false) Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String dataNascimento) {

        LocalDate dataNasc = LocalDate.parse(dataNascimento);
        M_Usuarios professor;

        long startTime = System.currentTimeMillis();

        if (id != null) {
            // Atualiza professor existente
            professor = s_usuario.atualizarProfessor(id, nome, email, dataNasc);
            return ResponseEntity.ok(professor); // Retorna os dados do professor atualizado
        } else {
            // Cria um novo professor
            String senha = gerarSenha();
            professor = s_usuario.cadastrarUsuario(nome, email, senha, senha, dataNasc, 2);

            String mensagem = "Olá, " + nome + "!\n\n";
            mensagem += "Seja bem-vindo ao nosso sistema! 🎉\n\n";
            mensagem += "Estamos muito felizes em ter você conosco. Agora você pode acessar nossa plataforma e começar a explorar todas as funcionalidades que preparamos com muito carinho para você.\n\n";
            mensagem += "Aqui estão suas informações de acesso:\n";
            mensagem += "--------------------------------------------------\n";
            mensagem += "Email: " + email + "\n";
            mensagem += "Senha: " + senha + "\n";
            mensagem += "--------------------------------------------------\n\n";
            mensagem += "Por motivos de segurança, recomendamos que você altere sua senha após o primeiro acesso. 😉\n\n";
            mensagem += "Aproveite o melhor do nosso sistema e tenha uma excelente experiência! 🚀\n\n";
            mensagem += "Atenciosamente,\n";
            mensagem += "Equipe do Sistema\n";
            mensagem += "--------------------------------------------------\n";
            mensagem += "P.S: Estamos ansiosos para ver o que você vai conquistar com a nossa plataforma!";

            s_email.enviaEmail(email, "Bem-vindo ao sistema!", mensagem);
            return ResponseEntity.ok(professor); // Retorna os dados do novo professor
        }
    }

    // Endpoint para deletar professor
    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        boolean deleted = s_usuario.deleteProfessor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para obter professor por ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<M_Usuarios> obterProfessor(@PathVariable Long id) {
        M_Usuarios professor = s_usuario.obterProfessorPorId(id);
        if (professor != null) {
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
        }
    }

    // Endpoint para listar todos os professores
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<M_Usuarios>> listarProfessores() {
        List<M_Usuarios> professores = s_usuario.obterProfessores(); // Chama o método obterProfessores
        if (professores.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver professores
        } else {
            return ResponseEntity.ok(professores); // Retorna a lista de professores
        }


    }



    private String gerarSenha() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
