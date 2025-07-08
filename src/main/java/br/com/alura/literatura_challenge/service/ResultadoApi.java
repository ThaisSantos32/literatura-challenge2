package br.com.alura.literatura_challenge.service;

import br.com.alura.literatura_challenge.model.DadosLivro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoApi(
        @JsonAlias("count") int total,
        @JsonAlias("next") String proximaPagina,
        @JsonAlias("previous") String paginaAnterior,
        @JsonAlias("results") List<DadosLivro> livros
) {}