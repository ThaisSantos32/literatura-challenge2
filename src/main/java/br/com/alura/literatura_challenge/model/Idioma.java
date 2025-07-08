package br.com.alura.literatura_challenge.model;
public enum Idioma {
    PT("pt"),
    EN("en"),
    ES("es"),
    FR("fr"),
    DESCONHECIDO("desconhecido");

    private String idiomaGutendex;

    Idioma(String idiomaGutendex) {
        this.idiomaGutendex = idiomaGutendex;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a string: " + text);
    }

    public String getIdiomaGutendex() {
        return idiomaGutendex;
    }
}