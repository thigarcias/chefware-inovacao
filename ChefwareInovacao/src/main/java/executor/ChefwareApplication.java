package executor;

import looca.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import track.Log;

@SpringBootApplication
public class ChefwareApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ChefwareApplication.class, args);
		Log log = new Log();
		Log.printLog("Inicializando a aplicação Chefware");
		Thread.sleep(2000);
		Principal.main(new String[]{});

	}
}
