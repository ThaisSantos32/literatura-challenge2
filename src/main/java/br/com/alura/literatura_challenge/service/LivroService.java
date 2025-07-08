package br.com.alura.literatura_challenge.service;
import br.com.alura.literatura_challenge.model.*;
import br.com.alura.literatura_challenge.repository.AutorRepository;
import br.com.alura.literatura_challenge.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indica que esta é uma classe de serviço e será gerenciada pelo Spring
public class LivroService {

    private final String ENDERECO_API = "https://gutendex.com/books/?search=";

    // Injeção de dependências: Spring vai fornecer instâncias dessas classes
    private ConsumirApi consumoApi;
    private IConverteDados conversor;
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    // Construtor para injeção de dependências
    public LivroService(ConsumirApi consumoApi, IConverteDados conversor,
                        LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // --- Métodos de Lógica de Negócio ---

    public void buscarLivroESalvar(String tituloLivro) {
        // Codifica o título para usar na URL (ex: espaços viram %20)
        String enderecoBusca = ENDERECO_API + tituloLivro.trim().replace(" ", "+");

        System.out.println("Buscando livro: " + enderecoBusca);

        // 1. Consumir a API Gutendex
        String json = consumoApi.obterDados(enderecoBusca);
        // System.out.println("JSON recebido: " + json); // Para depuração

        // 2. Converter o JSON para o objeto ResultadoApi
        ResultadoApi resultado = conversor.obterDados(json, ResultadoApi.class);

        // 3. Processar o resultado
        if (resultado != null && !resultado.resultados().isEmpty()) {
            DadosLivro dadosLivro = resultado.resultados().get(0); // Pega o primeiro livro da lista

            // Verifica se o livro já existe no banco
            Optional<Livro> livroExistente = livroRepository.findByTituloContainsIgnoreCase(dadosLivro.titulo());
            if (livroExistente.isPresent()) {
                System.out.println("Livro '" + dadosLivro.titulo() + "' já está registrado no banco de dados.");
                return; // Sai do método se o livro já existe
            }

            // Mapear DadosAutor para Autor
            Autor autor = null;
            if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
                DadosAutor dadosAutor = dadosLivro.autores().get(0); // Assume um autor principal

                // Tenta encontrar o autor no banco de dados
                Optional<Autor> autorExistente = autorRepository.findByNomeContainsIgnoreCase(dadosAutor.nome());
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                    System.out.println("Autor '" + autor.getNome() + "' já existe no banco. Associando livro a ele.");
                } else {
                    autor = new Autor(dadosAutor.nome(), dadosAutor.anoNascimento(), dadosAutor.anoFalecimento());
                    // O CascadeType.PERSIST em Livro já cuidará de salvar o autor
                    System.out.println("Novo autor '" + autor.getNome() + "' será adicionado ao banco.");
                }
            } else {
                System.out.println("Autor não encontrado na API para este livro.");
            }

            // Mapear String de idioma para enum Idioma
            Idioma idioma = null;
            if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
                try {
                    idioma = Idioma.fromString(dadosLivro.idiomas().get(0)); // Pega o primeiro idioma
                } catch (IllegalArgumentException e) {
                    System.err.println("Idioma desconhecido: " + dadosLivro.idiomas().get(0) + ". Livro será salvo sem idioma específico.");
                    // Pode-se optar por não salvar, ou salvar com idioma nulo, dependendo da regra
                }
            }


            // 4. Criar a entidade Livro e associar o autor
            Livro livro = new Livro(dadosLivro.titulo(), autor, idioma, dadosLivro.numeroDownloads());

            // 5. Salvar no banco de dados
            livroRepository.save(livro);
            System.out.println("Livro salvo com sucesso!");
            System.out.println(livro); // Imprime os detalhes do livro salvo
        } else {
            System.out.println("Livro não encontrado na API Gutendex com o título: " + tituloLivro);
        }
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public void listarTodosAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no banco de dados.");
        } else {
            System.out.println("\n----- AUTORES REGISTRADOS -----");
            autores.forEach(System.out::println);
            System.out.println("-------------------------------");
        }
    }

    public void listarAutoresVivosNoAno(Integer ano) {
        List<Autor> autores = autorRepository.buscarAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano + " no banco de dados.");
        } else {
            System.out.println("\n----- AUTORES VIVOS NO ANO " + ano + " -----");
            autores.forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }

    public void listarLivrosPorIdioma(Idioma idioma) {
        List<Livro> livros = livroRepository.findByIdiomas(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma " + idioma.toString() + " no banco de dados.");
        } else {
            System.out.println("\n----- LIVROS EM IDIOMA " + idioma.toString() + " -----");
            livros.forEach(System.out::println);
            System.out.println("------------------------------------");
        }
    }
}