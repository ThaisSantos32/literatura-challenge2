package br.com.alura.literatura_challenge.service;
import br.com.alura.literatura_challenge.model.Idioma;
import br.com.alura.literatura_challenge.model.Livro;
import org.springframework.stereotype.Component; // Importar @Component

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component // Anotação para que o Spring gerencie esta classe como um bean
public class Principal {

    private final LivroService livroService; // Injetar o LivroService
    private Scanner teclado = new Scanner(System.in);

    // Construtor para injeção de dependência do LivroService
    public Principal(LivroService livroService) {
        this.livroService = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    -------------------------------------------------
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    -------------------------------------------------
                    """;
            System.out.println(menu);
            try {
                opcao = teclado.nextInt();
                teclado.nextLine(); // Consumir a nova linha

                switch (opcao) {
                    case 1:
                        buscarLivroPeloTitulo();
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEmAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo da aplicação. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção de 0 a 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                teclado.nextLine(); // Limpar o buffer do scanner
                opcao = -1; // Para garantir que o loop continue
            } catch (RuntimeException e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
            System.out.println("\nPressione ENTER para continuar...");
            teclado.nextLine(); // Esperar o usuário pressionar Enter antes de mostrar o menu novamente
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o título do livro que deseja buscar:");
        var tituloLivro = teclado.nextLine();
        livroService.buscarLivroESalvar(tituloLivro); // Chama o serviço
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroService.listarTodosLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado no banco de dados.");
        } else {
            System.out.println("\n----- LIVROS REGISTRADOS -----");
            livros.forEach(System.out::println);
            System.out.println("------------------------------");
        }
    }

    private void listarAutoresRegistrados() {
        livroService.listarTodosAutores(); // O serviço já imprime
    }

    private void listarAutoresVivosEmAno() {
        System.out.println("Digite o ano para listar autores vivos:");
        try {
            var ano = teclado.nextInt();
            teclado.nextLine(); // Consumir a nova linha
            livroService.listarAutoresVivosNoAno(ano); // O serviço já imprime
        } catch (InputMismatchException e) {
            System.out.println("Ano inválido! Por favor, digite um número inteiro.");
            teclado.nextLine(); // Limpar o buffer
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma para listar livros (ex: PT, EN, ES, FR):");
        var idiomaStr = teclado.nextLine().toUpperCase();
        try {
            Idioma idioma = Idioma.fromString(idiomaStr); // Converte a string para o enum Idioma
            livroService.listarLivrosPorIdioma(idioma); // O serviço já imprime
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}