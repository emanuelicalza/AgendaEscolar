package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.service.S_SalvarProva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class C_SalvarProva {

    private final S_SalvarProva s_salvarProva;

    @Autowired
    public C_SalvarProva(S_SalvarProva s_salvarProva) {
        this.s_salvarProva = s_salvarProva;
    }

    // Método para exibir todas as provas salvas
    @GetMapping("/provas")
    public String listarProvas(Model model) {
        List<M_SalvarProva> provas = s_salvarProva.buscarTodasProvas();
        model.addAttribute("provas", provas);  // Adiciona a lista de provas ao modelo
        return "listaProvas";  // Nome do template Thymeleaf que vai exibir os dados
    }

    @PostMapping("/salvarprova")
    public ResponseEntity<M_SalvarProva> salvarProva(@RequestParam("titulo") String titulo,
                                                     @RequestParam("descricao") String descricao,
                                                     @RequestParam("data") String data,
                                                     @RequestParam("type") String tipo,
                                                     @RequestParam(name = "id", required = false) Long id) {
        M_SalvarProva prova = s_salvarProva.salvarProva(titulo, descricao, data, tipo, id);
        if (prova != null) {
            return ResponseEntity.ok(prova);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Método para retornar provas em formato JSON
    @GetMapping("/listarprovas")
    public ResponseEntity<List<M_SalvarProva>> listarProvasJson() {
        List<M_SalvarProva> provas = s_salvarProva.buscarTodasProvas();
        return ResponseEntity.ok(provas);  // Certifique-se que o id está na lista retornada
    }

    @GetMapping("/buscarProvasJson")
    public ResponseEntity<List<M_SalvarProva>> buscarProvasJson() {
        List<M_SalvarProva> provas = s_salvarProva.buscarTodasProvas();
        return ResponseEntity.ok(provas);
    }

    // Método para deletar uma prova pelo ID
    @DeleteMapping("/evento/deletar/{id}")
    public ResponseEntity<String> deletarProva(@PathVariable Long id) {
        try {
            s_salvarProva.deletarProva(id);  // Chama o serviço para deletar a prova
            return ResponseEntity.ok("Prova deletada com sucesso");
        } catch (Exception e) {
            if (e.getMessage().equals("Prova não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar a prova");
            }
        }
    }
}
