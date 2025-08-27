package br.com.barbershop.barber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Service.AgendamentoService;
import br.com.barbershop.barber.Service.ColaboradorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTests {

    @Mock //Objeto falso do AgendamentoService.
    private AgendamentoService agendamentoService;

    @Spy //Criamos um "espião" do nosso service. Ele é real, mas podemos controlar alguns métodos.
    @InjectMocks //Injeta o mock acima no nosso espião.
    private ColaboradorService colaboradorService;

    @Test
    void deveAdicionarColaboradorComSucesso() {
       

        //Criamos o colaborador que queremos adicionar.
        Colaborador novoColaborador = new Colaborador(1, "Gabriel", "senha123");

        //Criamos uma lista vazia para simular o estado do arquivo antes da adição.
        List<Colaborador> listaAntes = new ArrayList<>();

        //Damos as "instruções secretas" para o nosso espião (spy):
        //"Quando o método carregarDados() for chamado, não leia o arquivo,
        // em vez disso, retorne a nossa lista vazia."
        doReturn(listaAntes).when(colaboradorService).carregarDados();

        //"Quando o método salvarDados() for chamado, não escreva no arquivo,
        //não faça nada " O `any()` significa que não importa a lista que ele receba.
        doNothing().when(colaboradorService).salvarDados(any());

        //Criamos um "capturador" para pegar a lista que será enviada para o salvarDados().
        ArgumentCaptor<List<Colaborador>> capturadorDeLista = ArgumentCaptor.forClass(List.class);


        

        //Chamamos o método que queremos testar.
        colaboradorService.adicionarColaborador(novoColaborador);

        //Verificamos se o método salvarDados() foi chamado exatamente 1 vez
        //e capturamos a lista que foi passada para ele.
        verify(colaboradorService, times(1)).salvarDados(capturadorDeLista.capture());

        //Pegamos a lista que foi capturada.
        List<Colaborador> listaDepois = capturadorDeLista.getValue();

        //Agora, fazemos as verificações (assertions) para ver se a lógica funcionou:
        assertFalse(listaDepois.isEmpty(), "A lista não deveria estar vazia após a adição.");
        assertEquals(1, listaDepois.size(), "A lista deveria ter exatamente 1 colaborador.");
        assertEquals("Gabriel", listaDepois.get(0).getNome(), "O nome do colaborador na lista está incorreto.");
    }
}
