package br.com.barbershop.barber.Model;

public class Cliente {
    private String nome;
    private String telefone;
    // Constructor
    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }


//getters
public String getNome() {
    return nome;
}

public String getTelefone() {
    return telefone;
}
}