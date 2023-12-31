package executor;

import looca.ChefwareMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import track.Log;

import java.io.IOException;

@SpringBootApplication
public class ChefwareApplication {

	public static void main(String[] args) throws InterruptedException, IOException {
		SpringApplication.run(ChefwareApplication.class, args);
		Log log = new Log();
		Log.printLog("[CHEFWARE] Inicializando a aplicação Chefware");
		Thread.sleep(2000);
		ChefwareMain.startChefware();
	}
}
