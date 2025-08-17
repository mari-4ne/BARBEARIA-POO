package com.barbearia.Service;

import com.barbearia.Model.Agendamento;
import com.barbearia.Model.Colaborador;
import com.barbearia.Model.DiaDaSemana;
import com.barbearia.Util.PersistenciaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AgendamentoService extends PersistenciaTemplate<Agendamento> {

    private List<Agendamento> agendamentos;
    private final ColaboradorService colaboradorService;


    @Autowired
    public AgendamentoService(ColaboradorService colaboradorService) {
        super("agendamentos.json");
        this.colaboradorService = colaboradorService; 
        this.agendamentos = carregarDados(); 
    }

    @Override
    protected Class<Agendamento> getTipoClasse() {
        return Agendamento.class;
    }

    @Override
    protected List<Agendamento> inicializarDadosPadrao() {
        //se o arquivo agendamentos.json não existir, começa com uma lista vazia.
        return new ArrayList<>();
    }
    public void agendar(Agendamento novoAgendamento) {
        if (verificarHorarioOcupado(novoAgendamento)) {
            throw new RuntimeException("Horário indisponível para este colaborador!");
        }

        //salva o novo agendamento
        agendamentos.add(novoAgendamento);
        salvarDados(agendamentos);

        //remove o horário da lista de disponibilidade do colaborador
        int colaboradorId = novoAgendamento.getColaborador().getId();
        LocalDateTime dataHora = novoAgendamento.getDataHora();
        DiaDaSemana dia = converterParaMeuDiaDaSemana(dataHora.getDayOfWeek());
        String horario = dataHora.format(DateTimeFormatter.ofPattern("HH:mm"));

        colaboradorService.removerHorarioDisponivel(colaboradorId, dia, horario);
    }

   
    public List<Agendamento> listarTodos() {
        return Collections.unmodifiableList(agendamentos);
    }

    private boolean verificarHorarioOcupado(Agendamento novoAgendamento) {
        return agendamentos.stream().anyMatch(agendamentoExistente ->
                agendamentoExistente.getColaborador().equals(novoAgendamento.getColaborador()) &&
                agendamentoExistente.getDataHora().isEqual(novoAgendamento.getDataHora())
        );
    }

    // Converte o Enum padrão do Java (DayOfWeek) para DiaDaSemana

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