package br.com.barbershop.barber.Model;

public class FazerBarba extends Servico {

    private String estilo;

    // Constructor
    public FazerBarba(String nome, double preco, String estilo) {
        super(preco, nome);
        this.estilo = estilo;
    }

    // Getters
    public String getEstilo() {
        return estilo;
    }
    
}
