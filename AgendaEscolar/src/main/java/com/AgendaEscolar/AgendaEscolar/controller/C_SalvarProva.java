package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.service.S_SalvarProva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // Método para salvar a prova
    @PostMapping("/salvarprova")
    public String salvarProva(@RequestParam("titulo") String titulo,
                              @RequestParam("descricao") String descricao,
                              @RequestParam("data") String data,
                              @RequestParam("type") String tipo,
                              @RequestParam(name = "id", required = false) Long id, Model model) {
        M_SalvarProva prova = s_salvarProva.salvarProva(titulo, descricao, data, tipo, id);
        if (prova != null) {
            model.addAttribute("mensagem", "Prova salva com sucesso!");
        } else {
            model.addAttribute("mensagem", "Erro ao salvar a prova.");
        }
        return "resultado";  // Nome do template Thymeleaf que mostrará a mensagem
    }
}
