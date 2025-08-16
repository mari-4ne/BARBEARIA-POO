package Model;

public enum TipoCorte {
    MOICANO("Moicano", 25.00),
    SURFISTA("Surfista", 20.00),
    DEGRADE("Degradê", 25.00),
    SOCIAL("Social", 20.00),
    MULLET("Mullet", 25.00),
    BUSCUT("Buzz Cut", 25.00), 
    FADE("Fade", 25.00);

    private final String nome;
    private final double preco;

    TipoCorte(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters
}
