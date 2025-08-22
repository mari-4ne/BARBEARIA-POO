package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.barbearia.Model.Agendamento;
import com.barbearia.Service.AgendamentoService;
import com.barbearia.Service.ColaboradorService;
import com.barbearia.Service.impl.AgendamentoServiceImpl;

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
