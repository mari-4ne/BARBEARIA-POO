package br.com.barbershop.barber.Service;

import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Util.PersistenciaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService extends PersistenciaTemplate<Colaborador> {

    private final AgendamentoService agendamentoService;

    
    @Autowired
    public ColaboradorService(@Lazy AgendamentoService agendamentoService) {
        super("colaboradores.json");
        this.agendamentoService = agendamentoService;
    }

    @Override
    protected Class<Colaborador> getTipoClasse() {
        return Colaborador.class;
    }

    @Override//Inicializa e retorna uma lista de colaboradores
    protected List<Colaborador> inicializarDadosPadrao() {
        return new ArrayList<>(ListaColaboradores.getColaboradores());
    }

    //Carrega e retorna a lista de todos os colaboradores cadastrados
    public List<Colaborador> listarTodosColaboradores() {
        return carregarDados();
    }

    public Optional<Colaborador> buscarPorId(int id) {
        return carregarDados().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    //Busca um colaborador pelo seu nome
    public Optional<Colaborador> buscarPorNome(String nome) {
        return carregarDados().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    //Adiciona um novo colaborador à lista de colaboradores
    public void adicionarColaborador(Colaborador novoColaborador) {
        List<Colaborador> colaboradores = carregarDados(); // Carrega a lista atual
        if (colaboradores.stream().anyMatch(c -> c.getId() == novoColaborador.getId())) {
            throw new RuntimeException("Já existe um colaborador com este ID");
        }
        colaboradores.add(novoColaborador);
        salvarDados(colaboradores); // Salva a lista modificada
    }

    //Atualiza as informações de um colaborador existente.
    public void atualizarColaborador(Colaborador colaboradorAtualizado) {
        List<Colaborador> colaboradores = carregarDados(); // Carrega a lista atual
        colaboradores.removeIf(c -> c.getId() == colaboradorAtualizado.getId());
        colaboradores.add(colaboradorAtualizado);
        salvarDados(colaboradores);
    }

    
    public void removerColaborador(int id) {
        List<Colaborador> colaboradores = carregarDados(); // Carrega a lista atual
        colaboradores.removeIf(c -> c.getId() == id);
        salvarDados(colaboradores);
    }

    //Remove um horário específico da lista de horários disponíveis de um colaborador
    public void removerHorarioDisponivel(int colaboradorId, DiaDaSemana dia, String horario) {
        List<Colaborador> colaboradores = carregarDados(); // Carrega a lista atual
        Optional<Colaborador> colaboradorOpt = colaboradores.stream()
                .filter(c -> c.getId() == colaboradorId).findFirst();

        if (colaboradorOpt.isPresent()) {
            Colaborador colaborador = colaboradorOpt.get();
            colaborador.removerHorario(dia, horario);
            salvarDados(colaboradores); // Salva a lista modificada
        } else {
            throw new RuntimeException("Colaborador não encontrado!");
        }
    }

    //Retorna a lista de horários disponíveis de um colaborador para um dia
    public List<String> getHorariosDoColaborador(int colaboradorId, DiaDaSemana dia) {
        return buscarPorId(colaboradorId) 
                .map(colaborador -> colaborador.getHorariosDisponiveis(dia))
                .orElse(new ArrayList<>());
    }
    
    //Adiciona um novo horário à lista de horários disponíveis de um colaborador
    public void adicionarHorarioDisponivel(int colaboradorId, DiaDaSemana dia, String horario) {
        List<Colaborador> colaboradores = carregarDados();
        Optional<Colaborador> colaboradorOpt = colaboradores.stream()
                .filter(c -> c.getId() == colaboradorId).findFirst();

        if (colaboradorOpt.isPresent()) {
            Colaborador colaborador = colaboradorOpt.get();
            colaborador.adicionarHorario(dia, horario);
            salvarDados(colaboradores);

            
            agendamentoService.cancelarAgendamentoPorHorario(colaboradorId, dia, horario);
        } else {
            throw new RuntimeException("Colaborador não encontrado!");
        }
    }
}
