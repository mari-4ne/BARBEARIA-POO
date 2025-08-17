package com.barbearia.Service;
import com.barbearia.Model.Colaborador;
import com.barbearia.Model.Servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class ListaColaboradores {
     private static final List<Colaborador> COLABORADORES;
     static{
            List<Colaborador> colaboradores = new ArrayList<>();
            colaboradores.add(new Colaborador(00001, "Joao", "1600825"));
            colaboradores.add(new Colaborador(00002, "Celso", "NY11092001"));
            colaboradores.add(new Colaborador(00003, "Nicolas", "URSS30121922"));
            colaboradores.add(new Colaborador(00004, "Mario", "BA02071822"));


            COLABORADORES = Collections.unmodifiableList(colaboradores);

     }

        public static List<Colaborador> getColaboradores() {
            return COLABORADORES;
        }
    
}
