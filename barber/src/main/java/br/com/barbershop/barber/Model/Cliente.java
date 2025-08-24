package br.com.barbershop.barber.Model;

public class Cliente {
    private String nome;
    private String telefone;
    // Constructor
    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

//construtor vazio
public Cliente() {}

//getters
public String getNome() {
    return nome;
}

public String getTelefone() {
    return telefone;
}

//setters
public void setNome(String nome) {
    this.nome = nome;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}
}