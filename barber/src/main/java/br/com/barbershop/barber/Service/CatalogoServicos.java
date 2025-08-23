package br.com.barbershop.barber.Service;
import java.util.ArrayList;
import java.util.List;

import br.com.barbershop.barber.Model.CortarCabelo;
import br.com.barbershop.barber.Model.FazerBarba;
import br.com.barbershop.barber.Model.Servico;

import java.util.Collections;
public class CatalogoServicos {

    private static final List<Servico> SERVICOS_DISPONIVEIS;
     static {
        // inicializa a lista de serviços disponíveis
        List<Servico> servicos = new ArrayList<>();

        // todos os serviços pre-definidos
        servicos.add(new CortarCabelo("Corte Cabelo", 20.00, "Social"));
        servicos.add(new CortarCabelo("Corte Cabelo", 20.00, "Surfista"));
        servicos.add(new CortarCabelo("Corte Cabelo", 25.00, "Degrade"));
        servicos.add(new CortarCabelo("Corte Cabelo", 25.00, "Moicano"));
        servicos.add(new CortarCabelo("Corte Cabelo", 25.00, "Mullet"));
        servicos.add(new CortarCabelo("Corte Cabelo", 25.00, "Buzz Cut"));
        servicos.add(new CortarCabelo("Corte Cabelo", 25.00, "Fade"));

        servicos.add(new FazerBarba("Barba", 25.00,"Barba Simples" ));
        servicos.add(new FazerBarba("Barba", 40.00, "Barboterapia"));

        SERVICOS_DISPONIVEIS = Collections.unmodifiableList(servicos);
    }

    public static List<Servico> getServicosDisponiveis() {
        return SERVICOS_DISPONIVEIS;
    }
}
    

