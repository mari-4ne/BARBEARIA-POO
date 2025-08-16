package com.barbearia.model;

import java.time.LocalDateTime;

public class Agendamento {
    private Long id;
    private String nomeCliente;
    private String servicoDesejado;
    private LocalDateTime horario;
    private Colaborador colaborador; 
    
}