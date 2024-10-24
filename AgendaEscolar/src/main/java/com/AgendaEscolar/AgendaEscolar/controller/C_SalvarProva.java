package com.AgendaEscolar.AgendaEscolar.controller;

import com.AgendaEscolar.AgendaEscolar.model.M_SalvarProva;
import com.AgendaEscolar.AgendaEscolar.service.S_SalvarProva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_SalvarProva {

    private final S_SalvarProva s_salvarProva;

    @Autowired
    public C_SalvarProva(S_SalvarProva s_salvarProva) {
        this.s_salvarProva = s_salvarProva;
    }

    @PostMapping("/salvarprova")
    @ResponseBody
    public String salvarprova(@RequestParam("titulo") String titulo,
                              @RequestParam("descricao") String descricao,
                              @RequestParam("data") String data,
                              @RequestParam("type") String tipo,
                              @RequestParam(name = "id", required = false) Long id) {
        M_SalvarProva prova = s_salvarProva.salvarProva(titulo, descricao, data, null, id);
        return prova != null ? "Prova salva com sucesso!" : "Erro ao salvar a prova.";
    }
}
