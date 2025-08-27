package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.barbershop.barber.Model.Agendamento;
import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Service.AgendamentoService;
import br.com.barbershop.barber.Service.ColaboradorService;

import java.time.LocalDateTime;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTests {

    @Mock
    private ColaboradorService colaboradorService; // Objeto FALSO

    @InjectMocks
    private AgendamentoService agendamentoService; // Objeto REAL com o falso injetado

    @Test
    void deveAgendarClienteComSucesso() {

        // Criado objetos de mentira apenas para o teste

        Colaborador colaboradorFalso = new Colaborador(5, "Emanuel", "123456");
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setColaborador(colaboradorFalso);
        novoAgendamento.setDataHora(LocalDateTime.of(2025, 8, 25, 14, 0));

        // Executa o método que queremos testar
        agendamentoService.agendar(novoAgendamento);

        // Valida se o estado do objeto está correto
        assertEquals(colaboradorFalso, novoAgendamento.getColaborador());
        assertNotNull(novoAgendamento.getDataHora());

        // Também podemos verificar se o agendamentoService "conversou" com o
        // colaboradorService
        // para remover o horário disponível.
        verify(colaboradorService).removerHorarioDisponivel(5, br.com.barbershop.barber.Model.DiaDaSemana.SEGUNDA,
                "14:00");
    }
}
