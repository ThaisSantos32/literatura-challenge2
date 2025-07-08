package br.com.alura.literatura_challenge.service;

public interface IConverteDados {
    //"T" representa uma classe generica, usamos quando nao sabemos qual classe usar
    <T> T obterDados (String json, Class<T> classe);
}

