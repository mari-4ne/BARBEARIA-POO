package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.barbershop.barber.Model.Agendamento;
import br.com.barbershop.barber.Service.AgendamentoService;
import br.com.barbershop.barber.Service.ColaboradorService;
import br.com.barbershop.barber.Service.AgendamentoService;

public class AgendamentoServiceTests {



    private AgendamentoService agendamentoService;
    private ColaboradorService colaboradorService;
    private Agendamento agendamento;

    @Test
    void deveAgendarClienteComSucesso() {
        colaboradorService = new ColaboradorService();

        agendamentoService = new AgendamentoService(colaboradorService);
        agendamento = new Agendamento();
        agendamentoService.agendar(agendamento);
    }
}
