package com.barbearia.Model;

public class CortarCabelo extends Servico {

    private String tipodecorte;
    // Constructor
    public CortarCabelo(String nome, double preco, String estilo) {
        super(preco, nome);
        this.tipodecorte = estilo;
    }

    // Getter específico
    public String getEstilo() {
        return tipodecorte;
    }
}
