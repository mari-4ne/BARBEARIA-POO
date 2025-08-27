package br.com.barbershop.barber.Service;

import br.com.barbershop.barber.Model.Agendamento;
import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Util.PersistenciaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService extends PersistenciaTemplate<Agendamento> {

    
    private final ColaboradorService colaboradorService;

    @Autowired
    public AgendamentoService(ColaboradorService colaboradorService) {
        super("agendamentos.json");
        this.colaboradorService = colaboradorService;
    }

    @Override
    protected Class<Agendamento> getTipoClasse() {
        return Agendamento.class;
    }

    @Override
    protected List<Agendamento> inicializarDadosPadrao() {
        return new ArrayList<>();
    }

    
     // garante que a lista lida é a mais recente do arquivo JSON.
    public List<String> getHorariosAgendados(int colaboradorId, DiaDaSemana dia) {
        // Passo 1: Lê o estado MAIS ATUAL do arquivo agendamentos.json
        List<Agendamento> agendamentosAtuais = carregarDados();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //Filtra e retorna a lista atualizada
        return agendamentosAtuais.stream()
            .filter(agendamento -> agendamento.getColaborador().getId() == colaboradorId)
            .filter(agendamento -> converterParaMeuDiaDaSemana(agendamento.getDataHora().getDayOfWeek()) == dia)
            .map(agendamento -> agendamento.getDataHora().format(formatter))
            .collect(Collectors.toList());
    }

    
     //apaga o agendamento do arquivo.
    public void cancelarAgendamentoPorHorario(int colaboradorId, DiaDaSemana dia, String horario) {
        List<Agendamento> agendamentos = carregarDados();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        boolean foiRemovido = agendamentos.removeIf(agendamento ->
                agendamento.getColaborador().getId() == colaboradorId &&
                converterParaMeuDiaDaSemana(agendamento.getDataHora().getDayOfWeek()) == dia &&
                agendamento.getDataHora().format(timeFormatter).equals(horario)
        );

        if (foiRemovido) {
            salvarDados(agendamentos);
        }
    }


    public void agendar(Agendamento novoAgendamento) {
        List<Agendamento> agendamentos = carregarDados();
        if (agendamentos.stream().anyMatch(a -> a.getDataHora().isEqual(novoAgendamento.getDataHora()) && a.getColaborador().getId() == novoAgendamento.getColaborador().getId())) {
            throw new RuntimeException("Horário indisponível para este colaborador!");
        }
        agendamentos.add(novoAgendamento);
        salvarDados(agendamentos);

    
        // após agendar remove o horário da lista de disponibilidade do colaborador
        int colaboradorId = novoAgendamento.getColaborador().getId();
        LocalDateTime dataHora = novoAgendamento.getDataHora();
        DiaDaSemana dia = converterParaMeuDiaDaSemana(dataHora.getDayOfWeek());
        String horario = dataHora.format(DateTimeFormatter.ofPattern("HH:mm"));

        colaboradorService.removerHorarioDisponivel(colaboradorId, dia, horario);
       
    }
    private DiaDaSemana converterParaMeuDiaDaSemana(DayOfWeek diaDoJava) {
        switch (diaDoJava) {
            case MONDAY: return DiaDaSemana.SEGUNDA;
            case TUESDAY: return DiaDaSemana.TERCA;
            case WEDNESDAY: return DiaDaSemana.QUARTA;
            case THURSDAY: return DiaDaSemana.QUINTA;
            case FRIDAY: return DiaDaSemana.SEXTA;
            case SATURDAY: return DiaDaSemana.SABADO;
            case SUNDAY: return DiaDaSemana.DOMINGO;
            default: throw new IllegalArgumentException("Dia da semana inválido: " + diaDoJava);
        }
    }
}