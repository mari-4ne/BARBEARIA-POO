package br.com.barbershop.barber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.barbearia.Service.ColaboradorService;
import com.barbearia.Model.Colaborador;
public class ColaboradorServiceTests {

    private ColaboradorService colaboradorService = new ColaboradorService();
    private Colaborador colaborador = new Colaborador(1, "emanuel", "123456");


    @Test
    void deveAdicionarColaboradorComSucesso() {
        colaboradorService.adicionarColaborador(colaborador);
    }
}
