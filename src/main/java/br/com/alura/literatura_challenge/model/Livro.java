package br.com.alura.literatura_challenge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor; // Mapeamento ManyToOne para Autor

    @Enumerated(EnumType.STRING)
    private Idioma idiomas; // Enum para Idioma

    private Double downloadCount;

    // Construtor padrão para JPA
    public Livro() {}

    // Construtor a partir de DadosLivro
    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.downloadCount= Double.valueOf(dadosLivro.numeroDownloads());
        // O idioma precisa ser tratado como um enum.
        // Assumindo que dadosLivro.idiomas() retorna uma List<String> e queremos o primeiro.
        // Adicione uma verificação para garantir que a lista não está vazia.
        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
            try {
                this.idiomas = Idioma.fromString(dadosLivro.idiomas().get(0));
            } catch (IllegalArgumentException e) {
                System.err.println("Idioma desconhecido: " + dadosLivro.idiomas().get(0));
                this.idiomas = Idioma.DESCONHECIDO; // Ou defina como null, ou lance exceção
            }
        }
    }

    public Livro(String titulo, Autor autor, Idioma idiomas, Integer integer) {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idiomas;
    }

    public void setIdioma(Idioma idioma) {
        this.idiomas= idiomas;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }

    // <<<<<<<<<<< MÉTODO TOSTRING ADICIONADO AQUI >>>>>>>>>>
    @Override
    public String toString() {
        return "----- LIVRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNome() : "Desconhecido") + "\n" +
                "Idioma: " + idiomas + "\n" +
                "Número de downloads: " + downloadCount + "\n" +
                "-----------------\n";
    }
}