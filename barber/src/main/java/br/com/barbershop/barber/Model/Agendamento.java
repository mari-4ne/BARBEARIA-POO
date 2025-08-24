package br.com.barbershop.barber.Model;

import java.time.LocalDateTime;

public class Agendamento {
    private Cliente cliente = new Cliente();
    private String corteDesejado;
    private LocalDateTime horario;
    private Colaborador colaborador; 
    private DiaDaSemana dia; 


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

    public DiaDaSemana getDia() { return dia;
    }

    public String getCorteDesejado() {
        return corteDesejado;
    }
    
    
    //setters
    public void setColaborador(Colaborador colaborador) { 
        this.colaborador = colaborador;
    }

    public void setCliente(Cliente cliente) { 
        this.cliente = cliente;
    }

    public void setDataHora(LocalDateTime horario) { 
        this.horario = horario;
    }

    public void setCorteDesejado(String corteDesejado) { 
        this.corteDesejado = corteDesejado; 
    }

    public void setDia(DiaDaSemana dia) { 
        this.dia = dia;
    }

}