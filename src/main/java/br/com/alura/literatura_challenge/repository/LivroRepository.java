package br.com.alura.literatura_challenge.repository;

import br.com.alura.literatura_challenge.model.Idioma;
import br.com.alura.literatura_challenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainsIgnoreCase(String titulo);
    List<Livro> findByIdiomas(Idioma idioma);
}