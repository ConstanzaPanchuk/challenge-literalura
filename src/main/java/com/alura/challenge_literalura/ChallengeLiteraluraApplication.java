package com.alura.challenge_literalura;

import com.alura.challenge_literalura.principal.Principal;
import com.alura.challenge_literalura.repository.AutorRepository;
import com.alura.challenge_literalura.repository.LibroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

    private final Principal principal;
    public ChallengeLiteraluraApplication(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.principal = new Principal(libroRepository, autorRepository);
    }
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        principal.mostrarElMenu();
    }
}
