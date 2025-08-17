package com.barbearia.Service;

import com.barbearia.Model.Agendamento;
import com.barbearia.Model.Colaborador;
import com.barbearia.Model.Servico;
import com.barbearia.Service.CatalogoServicos;
import com.barbearia.Service.ListaColaboradores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendamentoService {

    // Lista em memória para guardar os agendamentos realizados.
    private final List<Agendamento> agendamentosRealizados = new ArrayList<>();

  
    public List<Servico> getServicosDisponiveis() {
        return CatalogoServicos.getServicosDisponiveis();
    }

    
    public List<Colaborador> getColaboradoresDisponiveis() {
        return ListaColaboradores.getColaboradores();
    }

    
    public List<Agendamento> getTodosOsAgendamentos() {
        return Collections.unmodifiableList(agendamentosRealizados);
    }

    //criar um novo agendamento.
     
    public void agendar(Agendamento novoAgendamento) {
        // o mesmo colaborador não pode estar em dois agendamentos no mesmo horário.
        boolean horarioOcupado = isHorarioOcupado(novoAgendamento.getColaborador(), novoAgendamento.getDataHora());

        if (horarioOcupado) {
            // Se o horário estiver ocupado, lança uma exceção 
            throw new RuntimeException("Horário indisponível! O colaborador " + novoAgendamento.getColaborador().getNome() + " já possui um agendamento neste horário.");
        }

        // Se o horário estiver livre, adiciona o novo agendamento à lista.
        this.agendamentosRealizados.add(novoAgendamento);
        System.out.println("Agendamento realizado com sucesso para " + novoAgendamento.getCliente().getNome() + "!");
    }

    // verificar a disponibilidade de um horário.
   
    private boolean isHorarioOcupado(Colaborador colaborador, LocalDateTime dataHora) {
        // Percorre a lista de agendamentos ja existentes
        for (Agendamento agendamentoExistente : this.agendamentosRealizados) {
            // Verifica se o agendamento é para o mesmo colaborador no mesmo horário
            if (agendamentoExistente.getColaborador().equals(colaborador) && agendamentoExistente.getDataHora().isEqual(dataHora)) {
                return true; // Encontrou um conflito, horário está ocupado.
            }
        }
        return false; // Não encontrou conflitos, horário está livre.
    }
}