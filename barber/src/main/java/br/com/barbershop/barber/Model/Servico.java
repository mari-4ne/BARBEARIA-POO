package br.com.barbershop.barber.Model;

public abstract class Servico {

    protected double preco;
    protected String nome;

    public Servico(double preco, String nome) {
        this.preco = preco;
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }



}
