package executor;

import looca.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChefwareApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ChefwareApplication.class, args);
		System.out.println("Iniciando a aplicação principal do chefware");
		Thread.sleep(2000);
		Principal.main(new String[]{});

	}

}
