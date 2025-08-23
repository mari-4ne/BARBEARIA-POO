package br.com.barbershop.barber.Service;

import java.util.Collections;
import java.util.List;

import br.com.barbershop.barber.Model.Colaborador;

import java.util.ArrayList;

public class ListaColaboradores {
    private static final List<Colaborador> COLABORADORES;
    
    static {
        List<Colaborador> colaboradores = new ArrayList<>();
        colaboradores.add(new Colaborador(1, "Joao", "1600825"));
        colaboradores.add(new Colaborador(2, "Celso", "NY11092001"));
        colaboradores.add(new Colaborador(3, "Nicolas", "URS530121922"));
        colaboradores.add(new Colaborador(4, "Mario", "BA02071822"));
        
        COLABORADORES = Collections.unmodifiableList(colaboradores);
    }

    public static List<Colaborador> getColaboradores() {
        return COLABORADORES;
    }
}