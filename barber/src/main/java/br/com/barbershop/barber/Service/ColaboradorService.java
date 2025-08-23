package br.com.barbershop.barber.Service;

import br.com.barbershop.barber.Model.Colaborador;
import br.com.barbershop.barber.Model.DiaDaSemana;
import br.com.barbershop.barber.Util.PersistenciaTemplate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ColaboradorService extends PersistenciaTemplate<Colaborador> {
private List<Colaborador> colaboradores;

    public ColaboradorService() {
        super("colaboradores.json");
        this.colaboradores = carregarDados(); // carrega do JSON ou inicializa
    }

    @Override
    protected Class<Colaborador> getTipoClasse() {
        return Colaborador.class;
    }

    @Override
    protected List<Colaborador> inicializarDadosPadrao() {
        // lista estática como fonte inicial
        return new ArrayList<>(ListaColaboradores.getColaboradores());
    }

    // métodos específicos do serviço
    public List<Colaborador> listarTodosColaboradores() {
        return new ArrayList<>(colaboradores);
    }

    public Optional<Colaborador> buscarPorId(int id) {
        return colaboradores.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public Optional<Colaborador> buscarPorNome(String nome) {
        return colaboradores.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public void adicionarColaborador(Colaborador novoColaborador) {
        // verifica se o ID já existe
        if (colaboradores.stream().anyMatch(c -> c.getId() == novoColaborador.getId())) {
            throw new RuntimeException("Já existe um colaborador com este ID");
        }
        
        colaboradores.add(novoColaborador);
        salvarDados(colaboradores); // persiste no JSON
    }

    public void atualizarColaborador(Colaborador colaboradorAtualizado) {
        colaboradores.removeIf(c -> c.getId() == colaboradorAtualizado.getId());
        colaboradores.add(colaboradorAtualizado);
        salvarDados(colaboradores);
    }

    public void removerColaborador(int id) {
        colaboradores.removeIf(c -> c.getId() == id);
        salvarDados(colaboradores);
    }

    //metodos para o controll
    public void removerHorarioDisponivel(int colaboradorId, DiaDaSemana dia, String horario) {
    //encontrar o colaborador
    Optional<Colaborador> colaboradorOpt = buscarPorId(colaboradorId);

    if (colaboradorOpt.isPresent()) {
        Colaborador colaborador = colaboradorOpt.get();
        // remover o horário da lista dele
        colaborador.removerHorario(dia, horario);
        // Salva a lista  atualizada
        salvarDados(colaboradores);
    } else {
        throw new RuntimeException("Colaborador não encontrado!");
    }
}

public void adicionarHorarioDisponivel(int colaboradorId, DiaDaSemana dia, String horario) {
    Optional<Colaborador> colaboradorOpt = buscarPorId(colaboradorId);

    if (colaboradorOpt.isPresent()) {
        Colaborador colaborador = colaboradorOpt.get();
        colaborador.adicionarHorario(dia, horario);
        salvarDados(this.colaboradores);
    } else {
        throw new RuntimeException("Colaborador não encontrado!");
    }
}

public List<String> getHorariosDoColaborador(int colaboradorId, DiaDaSemana dia) {
    return buscarPorId(colaboradorId)
            .map(colaborador -> colaborador.getHorariosDisponiveis(dia))
            .orElse(new ArrayList<>());
}
}