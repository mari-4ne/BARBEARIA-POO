package br.com.barbershop.barber.Controller; // Supondo que você tenha um pacote Controller

import br.com.barbershop.barber.Model.Agendamento;
import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Model.CortarCabelo;
import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Model.Servico;
import br.com.barbershop.barber.Service.AgendamentoService;
import br.com.barbershop.barber.Service.CatalogoServicos;
import br.com.barbershop.barber.Service.ColaboradorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agendamento")
@SessionAttributes("agendamento")
//com os passos
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final ColaboradorService colaboradorService;

    
    public AgendamentoController(AgendamentoService agendamentoService, ColaboradorService colaboradorService) {
        this.agendamentoService = agendamentoService;
        this.colaboradorService = colaboradorService;
    }

    //inicializa um novo objeto Agendamento
    @ModelAttribute("agendamento")
    public Agendamento inicializarAgendamento() {
        return new Agendamento();
    }

    //passo 1
    @GetMapping("/passo1-barbeiro")
    public String mostrarPasso1(Model model) {
        List<Colaborador> colaboradores = colaboradorService.listarTodosColaboradores();
        model.addAttribute("colaboradores", colaboradores);
        return "barbeiro"; //arquivo barbeiro.html
    }

        @PostMapping("/passo1-barbeiro")
    public String processarPasso1(@RequestParam("colaboradorId") int colaboradorId,
                                  @ModelAttribute("agendamento") Agendamento agendamento) {
        Colaborador colaborador = colaboradorService.buscarPorId(colaboradorId).orElse(null);
        agendamento.setColaborador(colaborador); // salva o barbeiro escolhido no objeto da sessão
        return "redirect:/agendamento/passo2-dia"; // Redireciona para o próximo passo
    }

    //passo 2
     @GetMapping("/passo2-dia")
    public String mostrarPasso2(@ModelAttribute("agendamento") Agendamento agendamento, Model model) {
        if (agendamento.getColaborador() == null) {
            return "redirect:/agendamento/passo1-barbeiro"; //redireciona se o barbeiro não estiver selecionado
        }

           
          Colaborador colaboradorEscolhido = agendamento.getColaborador();
          //filtra os dias disponiveis em uma nova lists
          List<DiaDaSemana> diasDisponiveis = colaboradorEscolhido.getDisponibilidade().entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty()) 
            .map(entry -> entry.getKey())                
            .collect(Collectors.toList());                

   
        model.addAttribute("diasDisponiveis", diasDisponiveis);
        //model.addAttribute("diasDaSemana", DiaDaSemana.values());
        return "dia"; // arquivo dia.html
    }

    @PostMapping("/passo2-dia")
    public String processarPasso2(@RequestParam("dia") DiaDaSemana dia, @ModelAttribute("agendamento") Agendamento agendamento) {
        agendamento.setDia(dia);
        return "redirect:/agendamento/passo3-horario";
    }

    //passo 3
    @GetMapping("/passo3-horario")
    public String mostrarPasso3(@ModelAttribute("agendamento") Agendamento agendamento, Model model) {
        if (agendamento.getDia() == null) {
            return "redirect:/agendamento/passo2-dia";
        }
  
    Colaborador colaboradorEscolhido = agendamento.getColaborador();
    DiaDaSemana diaEscolhido = agendamento.getDia();

    // filtra os horarios disponiveis para o dia e colaborador
    List<String> horarios = colaboradorService.getHorariosDoColaborador(colaboradorEscolhido.getId(), diaEscolhido);


    model.addAttribute("horariosDisponiveis", horarios);

        return "horario"; // Renderiza a página para escolher o horário
    }

    @PostMapping("/passo3-horario")
    public String processarPasso3(@RequestParam("horario") String horarioStr,
                                  @ModelAttribute("agendamento") Agendamento agendamento) {

        //converte a String do 
        LocalTime horarioEscolhido = LocalTime.parse(horarioStr);
        DiaDaSemana diaEscolhido = agendamento.getDia();

        //calcula a data
        LocalDate hoje = LocalDate.now();
        LocalDate proximaData = hoje.with(TemporalAdjusters.nextOrSame(diaEscolhido.getJavaDayOfWeek()));

        // combina a data e o horrario
        LocalDateTime dataHoraFinal = LocalDateTime.of(proximaData, horarioEscolhido);
        agendamento.setDataHora(dataHoraFinal);
        return "redirect:/agendamento/passo4-servico";
    }

    //passo 4
    @GetMapping("/passo4-servico")
     public String mostrarPasso4(@ModelAttribute("agendamento") Agendamento agendamento) {
      if (agendamento.getDataHora() == null) {
        return "redirect:/agendamento/passo3-horario";
      }
        return "serviço";
    }

    @PostMapping("/processar-passo4-servico")
    public String processarPasso4(@RequestParam("servicoEscolhido") String servico, @ModelAttribute("agendamento") Agendamento agendamento) {

    if (agendamento.getDataHora() == null) {
        return "redirect:/agendamento/passo3-horario";
    }
    //redicionamento serviço
    if ("CABELO".equals(servico)) {
        return "redirect:/agendamento/passo5-tipo-corte";
    } else if ("BARBA".equals(servico)) {
        agendamento.setCorteDesejado("Barba"); // Usando o campo existente por enquanto
        return "redirect:/agendamento/confirmacao";
    }

    // Se algo der errado, volta para o início do fluxo
    return "redirect:/agendamento/passo1-barbeiro";
}

    //passso 5
    @GetMapping("/passo5-tipo-corte")
public String mostrarPasso5(Model model, @ModelAttribute("agendamento") Agendamento agendamento) {
    // checa se o horario foi selecionado
        if (agendamento.getDataHora() == null) {
        return "redirect:/agendamento/passo3-horario";
    }

    List<Servico> todosOsServicos = CatalogoServicos.getServicosDisponiveis();
    //filtra a lista
    List<CortarCabelo> tiposDeCorte = todosOsServicos.stream()
            .filter(servico -> servico instanceof CortarCabelo) 
            .map(servico -> (CortarCabelo) servico)           
            .collect(Collectors.toList());               

    model.addAttribute("tiposDeCorte", tiposDeCorte);
    return "cortes"; 
   }
  
   //processa a escolha do tipo de corte
   @PostMapping("/passo5-tipo-corte")
   public String processarPasso5(@RequestParam("corteEscolhido") String nomeDoCorte,  @ModelAttribute("agendamento") Agendamento agendamento) {

    // checa se o horario foi selecionado
    if (agendamento.getDataHora() == null) {
        return "redirect:/agendamento/passo3-horario";
    }

    // salva o corte desejado no agendamento
    agendamento.setCorteDesejado(nomeDoCorte);
    // redireciona para a confirmação
    return "redirect:/agendamento/confirmacao";
    }

    //PASSO 6 CONFIRMACAO E SALVAMENTO
    @GetMapping("/confirmacao")
    public String mostrarPaginaDeConfirmacao(@ModelAttribute("agendamento") Agendamento agendamento) {
    if (agendamento.getDataHora() == null) {
        // garante que o fluxo foi seguido corretamente
        return "redirect:/agendamento/passo1-barbeiro";
    }
    
    return "confirmacao"; //arquivo confirmacao.html
}

    //salva o agendamento no banco de dados
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute("agendamento") Agendamento agendamento, SessionStatus status) {
    try {
        // o objeto "agendamento" já foi atualizado pelo Spring com os dados do cliente
        agendamentoService.agendar(agendamento); // salva no banco
        status.setComplete(); // limpa os dados da sessão após o sucesso
    } catch (RuntimeException e) {
        
        return "redirect:/agendamento/passo1-barbeiro?error=" + e.getMessage();
    }
    
    // rediciona para a página de sucesso
    return "redirect:/agendamento/sucesso";
    }

    @GetMapping("/sucesso")
     public String mostrarPaginaDeSucesso() {
      return "sucesso"; //pagina sucesso.html
    }
 



    




}