package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Service.ColaboradorService;
public class ColaboradorServiceTests {

    private ColaboradorService colaboradorService = new ColaboradorService();
    private Colaborador colaborador = new Colaborador(6, "gabriel", "1234568");


    @Test
    void deveAdicionarColaboradorComSucesso() {
        colaboradorService.adicionarColaborador(colaborador);
    }
}
