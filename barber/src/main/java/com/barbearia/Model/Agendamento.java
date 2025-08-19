package com.barbearia.Model;

import java.time.LocalDateTime;

public class Agendamento {
    private Cliente cliente;
    private String corteDesejado;
    private LocalDateTime horario;
    private Colaborador colaborador; 


    //construtor vazio para formulario
    public Agendamento() {
    }

    public Agendamento( Cliente cliente, String corteDesejado, LocalDateTime horario, Colaborador colaborador) {
        this.cliente = cliente;
        this.corteDesejado = corteDesejado;
        this.horario = horario;
        this.colaborador = colaborador;
    }

    //getters
    public Colaborador getColaborador() {
        return colaborador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHora() {
        return horario;
    }
    
}