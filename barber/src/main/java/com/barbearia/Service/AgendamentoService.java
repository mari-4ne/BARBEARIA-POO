package com.barbearia.Service;

import com.barbearia.Model.Agendamento;
import com.barbearia.Model.Colaborador;
import com.barbearia.Util.ManipuladorArquivoJson;
import com.barbearia.Util.PersistenciaTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendamentoService extends PersistenciaTemplate<Agendamento>{

    private List<Agendamento> agendamentos;

    public AgendamentoService() {
        super("agendamentos.json");
        this.agendamentos = carregarDados(); // chama o template 
    }

    @Override
    protected Class<Agendamento> getTipoClasse() {
        return Agendamento.class;
    }

    @Override
    protected List<Agendamento> inicializarDadosPadrao() {
        return new ArrayList<>(); // retorna lista vazia para agendamentos
    }

    // métodos específicos de agendamento
    public void agendar(Agendamento novoAgendamento) {
        if (verificarHorarioOcupado(novoAgendamento)) {
            throw new RuntimeException("Horário indisponível!");
        }
        agendamentos.add(novoAgendamento);
        salvarDados(agendamentos); // usa o template method
    }

    private boolean verificarHorarioOcupado(Agendamento novoAgendamento) {
        return agendamentos.stream().anyMatch(a -> 
            a.getColaborador().equals(novoAgendamento.getColaborador()) &&
            a.getDataHora().isEqual(novoAgendamento.getDataHora()));
    }

    public List<Agendamento> listarTodos() {
        return Collections.unmodifiableList(agendamentos);
    }
}