package com.barbearia.Controller; // Supondo que você tenha um pacote Controller

import com.barbearia.Model.Agendamento;
import com.barbearia.Model.Colaborador;
import com.barbearia.Service.AgendamentoService;
import com.barbearia.Service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {

    // 1. Injetando as dependências dos seus serviços
    private final AgendamentoService agendamentoService;
    private final ColaboradorService colaboradorService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService, ColaboradorService colaboradorService) {
        this.agendamentoService = agendamentoService;
        this.colaboradorService = colaboradorService;
    }

    //metodo para exibir a página com o formulário de agendamento.
    
    @GetMapping("/novo")
    public String mostrarFormularioDeAgendamento(Model model) {
        // busca a lista de colaboradores
        List<Colaborador> colaboradores = colaboradorService.listarTodosColaboradores();

        //para adicionar os colaboradores e o objeto Agendamento ao modelo
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("agendamento", new Agendamento()); // Objeto para o formulário

        return "formulario"; //retorna o nome do arquivo
    }

    // metodo para processar o envio do formulário e salvar o agendamento
   
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        try {
            //chama o método de agendamento do serviço
            agendamentoService.agendar(agendamento);
        } catch (RuntimeException e) {
            //  tratar o erro, redirecionando de volta
            return "redirect:/agendamento/novo?error=" + e.getMessage();
        }

        // redireciona para uma página de sucesso
        return "redirect:/agendamento/confirmacao";
    }

    //metodo para exibir a página de confirmação de sucesso
    @GetMapping("/confirmacao")
    public String mostrarPaginaDeConfirmacao() {
        return "confirmacao"; //retorna o nome do arquivo 
    }
}