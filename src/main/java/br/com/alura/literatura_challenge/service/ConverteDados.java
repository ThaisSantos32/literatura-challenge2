package br.com.alura.literatura_challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service; // Importar a anotação @Service

@Service // Anotação para que o Spring gerencie essa classe
public class ConverteDados implements IConverteDados { // Implementa a interface
    private ObjectMapper mapper = new ObjectMapper();

    @Override // Sobrescreve o método da interface
    public <T> T obterDados(String json, Class<T> classe) { // Nome do método ajustado
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao converter JSON: " + e.getMessage());
            throw new RuntimeException("Erro ao processar JSON para " + classe.getSimpleName(), e);
        }
    }
}