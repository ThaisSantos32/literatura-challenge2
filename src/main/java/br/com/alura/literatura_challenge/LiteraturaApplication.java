package br.com.alura.literatura_challenge;

import br.com.alura.literatura_challenge.service.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Esta é a classe principal
public class LiteraturaApplication implements CommandLineRunner {
	// Note: Se o nome da sua classe principal for diferente, como LiteraturaChallengeApplication,
	// então use esse nome no lugar de LiteraluraApplication no SpringApplication.run()

	private final Principal principal;

    public LiteraturaApplication(Principal principal) {
        this.principal = principal;
    }

    // ESTE É O MÉTODO MAIN QUE VOCÊ PRECISA
	public static void main(String[] args) {
		// O nome da classe aqui deve ser o nome da sua classe principal @SpringBootApplication
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.exibeMenu();
	}
}