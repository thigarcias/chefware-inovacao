package looca;

import model.Querys;
import chefware.Servidor;
import track.Log;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChefwareMain {
    public static void startChefware() throws InterruptedException {
        Thread servidorThread = new Thread(() -> {
            try {
                Servidor.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Capturar interrupções
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String data = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
            System.out.println(data + " --- [CHEFWARE] Encerrando a aplicação");
            servidorThread.interrupt();
            Log.close();
        }));
        servidorThread.start();
        Thread.sleep(1000);

        // Looping de captura de dados
        Querys query = new Querys();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                query.operacao();
            } catch (Exception e) {
                Log.printLog(e.toString());
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}

