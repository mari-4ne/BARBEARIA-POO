package br.com.barbershop.barber.Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colaborador {
    private int id;
    private String nome;
    private String senha;
    private Map<DiaDaSemana, List<String>> disponibilidade;

    // Construtor
    public Colaborador(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.disponibilidade = new HashMap<>();
        inicializarDisponibilidadePadrao();
    }

    //construtor vazio
    public Colaborador() {
        this.disponibilidade = new HashMap<>();
        inicializarDisponibilidadePadrao();
    }

    //inicializar a disponibilidade com horários padrão
    private void inicializarDisponibilidadePadrao() {
        // Horários "
        List<String> horariosPadrao = List.of(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00"
        );

        // disponibilidade para os dias da semana ( Segunda a Sexta)
        for (DiaDaSemana dia : DiaDaSemana.values()) {
            if (dia != DiaDaSemana.SABADO && dia != DiaDaSemana.DOMINGO) {
                // ArrayList para cada dia para serem modificadas independentemente
                disponibilidade.put(dia, new ArrayList<>(horariosPadrao));
            } else {
                // Sem horários para Sábado e Domingo
                disponibilidade.put(dia, new ArrayList<>());
            }
        }
    }

    // adicionar um novo horário disponível
    public void adicionarHorario(DiaDaSemana dia, String horario) {
        // O método computeIfAbsent garante que a lista exista antes de adicionar um horário
        this.disponibilidade.computeIfAbsent(dia, k -> new ArrayList<>()).add(horario);
    }

    // remover um horário (quando for agendado)
    public void removerHorario(DiaDaSemana dia, String horario) {
        if (this.disponibilidade.containsKey(dia)) {
            this.disponibilidade.get(dia).remove(horario);
        }
    }

    // visualizar os horários disponíveis de um dia específico
    public List<String> getHorariosDisponiveis(DiaDaSemana dia) {
        return this.disponibilidade.getOrDefault(dia, new ArrayList<>());
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Map<DiaDaSemana, List<String>> getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Map<DiaDaSemana, List<String>> disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}