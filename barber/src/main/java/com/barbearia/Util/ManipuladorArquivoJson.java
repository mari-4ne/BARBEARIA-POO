package com.barbearia.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


 // serializar e desserializar objetos Java para formato JSON
@Component 
public class ManipuladorArquivoJson {
    // conversão entre objetos Java e JSON
    private final ObjectMapper mapeadorJson;

    public ManipuladorArquivoJson() {
        this.mapeadorJson = new ObjectMapper();
        // Registra módulo para suporte a classes de data/hora
        this.mapeadorJson.registerModule(new JavaTimeModule());
    }

    
     // Salva um objeto em um arquivo JSON
    public <T> void salvarEmArquivo(T objeto, String nomeArquivo) throws IOException {
        // Converte o objeto para JSON com formatação e salva no arquivo
        mapeadorJson.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), objeto);
    }

     //Carrega um objeto de um arquivo JSON
    public <T> T carregarDeArquivo(String nomeArquivo, Class<T> tipoClasse) throws IOException {
        File arquivo = new File(nomeArquivo);
        // Verifica se o arquivo existe antes de tentar ler
        if (arquivo.exists()) {
            return mapeadorJson.readValue(arquivo, tipoClasse);
        }
        return null;
    }
}