package br.com.barbershop.barber.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


 //define o template para serviços de persistência em JSON

public abstract class PersistenciaTemplate<T> {
    private final ObjectMapper mapeadorJson;
    private final String nomeArquivo;

    public PersistenciaTemplate(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.mapeadorJson = new ObjectMapper();
        this.mapeadorJson.registerModule(new JavaTimeModule());
    }

    // define o fluxo padrão de carregamento
    public final List<T> carregarDados() {
        try {
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists()) {
                List<T> dadosCarregados = mapeadorJson.readValue(arquivo, 
                    mapeadorJson.getTypeFactory().constructCollectionType(List.class, getTipoClasse()));
                return dadosCarregados != null ? dadosCarregados : new ArrayList<>();
            }
            return inicializarDadosPadrao();
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do arquivo " + nomeArquivo + ": " + e.getMessage());
            return inicializarDadosPadrao();
        }
    }

    // define o fluxo padrão de salvamento
    public final void salvarDados(List<T> dados) {
        try {
            mapeadorJson.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    // métodos abstratos que as subclasses vão implementar
    protected abstract Class<T> getTipoClasse();
    protected abstract List<T> inicializarDadosPadrao();
}