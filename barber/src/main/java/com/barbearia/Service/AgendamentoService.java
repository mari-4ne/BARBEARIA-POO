package com.barbearia.Service;

import com.barbearia.Model.Agendamento;
import com.barbearia.Model.Colaborador;
import com.barbearia.Util.ManipuladorArquivoJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendamentoService {

    // Guardar os agendamentos realizados no arquivo.
    private static final String ARQUIVOAGENDAMENTO = "agendamento.json";

    // Lista em memória dos agendamentos
    private final List<Agendamento> agendamentos;
    private final ManipuladorArquivoJson manipuladorJson;

  // construtor
    public AgendamentoService(ManipuladorArquivoJson manipuladorJson) {
        this.manipuladorJson = manipuladorJson;
        this.agendamentos = carregarAgendamentos();
    }

    //Carrega os agendamentos do arquivo JSON
    private List<Agendamento> carregarAgendamentos() {
        try {
            List<Agendamento> agendamentosCarregados = manipuladorJson.carregarDeArquivo(ARQUIVOAGENDAMENTO, List.class);
            // Retorna a lista carregada ou uma nova lista vazia se não existir
            return agendamentosCarregados != null ? agendamentosCarregados : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar agendamentos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //Salva a lista atual de agendamentos no arquivo JSO
    private void salvarAgendamentos() {
        try {
            manipuladorJson.salvarEmArquivo(agendamentos, ARQUIVOAGENDAMENTO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }
    // cria um novo agendamento
    public void agendar(Agendamento novoAgendamento) {
        boolean horarioOcupado = verificarHorarioOcupado(novoAgendamento.getColaborador(), novoAgendamento.getDataHora());

        if (horarioOcupado) {
            throw new RuntimeException("Horário indisponível! O colaborador " + 
                novoAgendamento.getColaborador().getNome() + 
                " já tem um agendamento neste horário.");
        }
        // Adiciona o novo agendamento e persiste no arquivo
        this.agendamentos.add(novoAgendamento);
        salvarAgendamentos();
        System.out.println("Agendamento realizado para " + 
            novoAgendamento.getCliente().getNome() + "!");
    }
    //Verifica se um colaborador já tem agendamento em determinado horário
    //retorna true se o horário estiver ocupado, false caso contrário
    private boolean verificarHorarioOcupado(Colaborador colaborador, LocalDateTime dataHora) {
        for (Agendamento agendamentoExistente : this.agendamentos) {
            if (agendamentoExistente.getColaborador().equals(colaborador) && 
                agendamentoExistente.getDataHora().isEqual(dataHora)) {
                return true;
            }
        }
        return false;
    }
    //Retorna uma lista imutável de todos os agendamentos
    public List<Agendamento> listarTodosAgendamentos() {
        return Collections.unmodifiableList(agendamentos);
    }
}