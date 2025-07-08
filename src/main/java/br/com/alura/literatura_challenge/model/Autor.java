package br.com.alura.literatura_challenge.model;

import jakarta.persistence.*;

import java.util.List;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    // <<<<<<<<<<<< ATENÇÃO AQUI: VERIFIQUE ESSES NOMES >>>>>>>>>>>>>>
    private Integer anoNascimento; // <<-- DEVE SER EXATAMENTE ASSIM
    private Integer anoFalecimento; // <<-- DEVE SER EXATAMENTE ASSIM

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>(); // Inicialize para evitar NullPointerException

    // Construtor padrão
    public Autor() {}

    // Construtor a partir de DadosAutor
    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public Autor(String nome, Integer integer, Integer integer1) {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() { // <<-- E ESTE GETTER
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) { // <<-- E ESTE SETTER
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() { // <<-- E ESTE GETTER
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) { // <<-- E ESTE SETTER
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        livro.setAutor(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- AUTOR -----\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Ano de Nascimento: ").append(anoNascimento).append("\n");
        sb.append("Ano de Falecimento: ").append(anoFalecimento != null ? anoFalecimento : "N/A").append("\n");
        sb.append("Livros: [");
        if (!livros.isEmpty()) {
            for (int i = 0; i < livros.size(); i++) {
                sb.append(livros.get(i).getTitulo());
                if (i < livros.size() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("Nenhum livro registrado");
        }
        sb.append("]\n");
        sb.append("-----------------\n");
        return sb.toString();
    }
}