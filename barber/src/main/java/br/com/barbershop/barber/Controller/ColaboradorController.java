package br.com.barbershop.barber.Controller;

import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Service.ColaboradorService;
import br.com.barbershop.barber.Service.ListaColaboradores;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/login-colaborador")
    public String loginColaborador() {
        return "login-colaborador"; // src/main/resources/templates/login-colaborador.html
    }

    @PostMapping("/login-colaborador")
    public String loginColaborador(@RequestParam String login,
            @RequestParam String password,
            Model model) {

        // validação simples
        var colaborador = ListaColaboradores.autenticar(login, password);
        model.addAttribute("colaborador", colaborador);

        if (colaborador != null) {
            return "redirect:/colaborador/agenda-colaborador/" + colaborador.getId();
        } else {
            model.addAttribute("erro", "Login ou senha inválidos!");
            return "login-colaborador"; // volta pro formulário com mensagem
        }
    }

    // processa a remoção de um horário.
    @PostMapping("/remover-horario")
    @ResponseBody
    public String removerHorario(@RequestParam int colaboradorId,
            @RequestParam DiaDaSemana dia,
            @RequestParam String horario) {
        // o Controller chama o Service.
        colaboradorService.removerHorarioDisponivel(colaboradorId, dia, horario);
        return "redirect:/colaborador/gerenciar/" + colaboradorId; // redireciona de volta
    }

    // processa a adição de um horário.
    @PostMapping("/adicionar-horario")
    @ResponseBody
    public ResponseEntity<String> adicionarHorario(
            @RequestParam int colaboradorId,
            @RequestParam DiaDaSemana dia,
            @RequestParam String horario) {

        colaboradorService.adicionarHorarioDisponivel(colaboradorId, dia, horario);
        return ResponseEntity.ok("Horário adicionado com sucesso");
    }

    @GetMapping("/agenda-colaborador/{id}")
    public String gerenciarHorarios(@PathVariable int id, Model model) {
        var colaborador = colaboradorService.buscarPorId(id).orElse(null);

        // Criar listas de horários por dia da semana
        var segunda = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEGUNDA);
        var terca = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.TERCA);
        var quarta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUARTA);
        var quinta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUINTA);
        var sexta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEXTA);

        segunda.sort(Comparator.naturalOrder());
        terca.sort(Comparator.naturalOrder());
        quarta.sort(Comparator.naturalOrder());
        quinta.sort(Comparator.naturalOrder());
        sexta.sort(Comparator.naturalOrder());

        // adicionar no Model para Thymeleaf
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("diasDaSemana", DiaDaSemana.values());

        model.addAttribute("horariosSegunda", segunda);
        model.addAttribute("horariosTerca", terca);
        model.addAttribute("horariosQuarta", quarta);
        model.addAttribute("horariosQuinta", quinta);
        model.addAttribute("horariosSexta", sexta);

        return "agenda-colaborador";
    }

    @GetMapping("/disponibilizar-horario/{id}")
    public String disponibilizarPage(@PathVariable int id, Model model) {

        var horarios = java.util.Arrays.asList(
                "08:00", "08:30", "09:00", "09:30",
                "10:00", "10:30", "11:00", "13:00",
                "13:30", "14:00", "14:30", "15:00",
                "15:30", "16:00");

        var colaborador = colaboradorService.buscarPorId(id).orElse(null);

        // Horários disponíveis
        var segundaDisponiveis = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEGUNDA);
        var tercaDisponiveis = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.TERCA);
        var quartaDisponiveis = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUARTA);
        var quintaDisponiveis = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUINTA);
        var sextaDisponiveis = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEXTA);

        // Horários indisponíveis = todos - disponíveis
        var segundaIndisp = horarios.stream()
                .filter(h -> !segundaDisponiveis.contains(h))
                .toList();

        var tercaIndisp = horarios.stream()
                .filter(h -> !tercaDisponiveis.contains(h))
                .toList();

        var quartaIndisp = horarios.stream()
                .filter(h -> !quartaDisponiveis.contains(h))
                .toList();

        var quintaIndisp = horarios.stream()
                .filter(h -> !quintaDisponiveis.contains(h))
                .toList();

        var sextaIndisp = horarios.stream()
                .filter(h -> !sextaDisponiveis.contains(h))
                .toList();

        // Enviar para view
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("diasDaSemana", DiaDaSemana.values());

        model.addAttribute("horariosSegunda", segundaIndisp);
        model.addAttribute("horariosTerca", tercaIndisp);
        model.addAttribute("horariosQuarta", quartaIndisp);
        model.addAttribute("horariosQuinta", quintaIndisp);
        model.addAttribute("horariosSexta", sextaIndisp);

        return "disponibilizar-horario";
    }

    @GetMapping("/indisponibilizar-horario/{id}")
    public String indisponibilizarPage(@PathVariable int id, Model model) {
        var colaborador = colaboradorService.buscarPorId(id).orElse(null);

        // Buscar horários disponíveis por dia da semana
        var segunda = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEGUNDA);
        var terca = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.TERCA);
        var quarta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUARTA);
        var quinta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.QUINTA);
        var sexta = colaboradorService.getHorariosDoColaborador(id, DiaDaSemana.SEXTA);

        // Enviar para view
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("diasDaSemana", DiaDaSemana.values());

        model.addAttribute("horariosSegunda", segunda);
        model.addAttribute("horariosTerca", terca);
        model.addAttribute("horariosQuarta", quarta);
        model.addAttribute("horariosQuinta", quinta);
        model.addAttribute("horariosSexta", sexta);

        return "indisponibilizar-horario"; // Thymeleaf vai montar a página
    }

    @PostMapping("/indisponibilizar/confirmar") // volta pra pagina da agenda com os horarios
    public String confirmarIndisponibilidade(
            @RequestParam int colaboradorId,
            @RequestParam("horarios") List<String> horarios) {

        for (String h : horarios) {
            // exemplo: "Segunda-08:00"
            String[] partes = h.split("-");
            String dia = partes[0];
            String hora = partes[1];

            colaboradorService.removerHorarioDisponivel(colaboradorId, DiaDaSemana.valueOf(dia.toUpperCase()), hora);
        }

        return "redirect:/colaborador/agenda-colaborador/" + colaboradorId;
    }

    @PostMapping("/disponibilizar/confirmar") // volta pra página da agenda com os horários
    public String confirmarDisponibilidade(
            @RequestParam int colaboradorId,
            @RequestParam("horarios") List<String> horarios) {

        for (String h : horarios) {
            // exemplo: "Segunda-08:00"
            String[] partes = h.split("-");
            String dia = partes[0];
            String hora = partes[1];

            colaboradorService.adicionarHorarioDisponivel(
                    colaboradorId,
                    DiaDaSemana.valueOf(dia.toUpperCase()),
                    hora);
        }

        return "redirect:/colaborador/agenda-colaborador/" + colaboradorId;
    }

}
