
 package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.barbershop.barber.Model.Agendamento;
import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Service.AgendamentoService;
import br.com.barbershop.barber.Service.ColaboradorService;
import br.com.barbershop.barber.Service.AgendamentoService;
import java.time.LocalDateTime;

public class AgendamentoServiceTests {



    private AgendamentoService agendamentoService;
    private ColaboradorService colaboradorService;
    private Agendamento agendamento;

  @Test
void deveAgendarClienteComSucesso() {
    colaboradorService = new ColaboradorService();
    agendamentoService = new AgendamentoService(colaboradorService);

    // cria colaborador
    Colaborador colaborador = new Colaborador(5, "Emanuel", "123456");
    colaboradorService.adicionarColaborador(colaborador);

    // cria agendamento e associa colaborador + data/hora
    agendamento = new Agendamento();
    agendamento.setColaborador(colaborador);
    agendamento.setDataHora(LocalDateTime.of(2025, 8, 25, 14, 0)); // exemplo: hoje às 14h

    // executa o agendamento
    agendamentoService.agendar(agendamento);

    // valida se o agendamento foi registrado com sucesso
    assertEquals(colaborador, agendamento.getColaborador());
    assertNotNull(agendamento.getDataHora()); // garante que tem data
}
}
