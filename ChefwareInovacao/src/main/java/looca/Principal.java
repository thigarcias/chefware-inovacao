package looca;

import banco.Querys;
import chefware.Servidor;
import com.github.britooo.looca.api.core.Looca;
import org.springframework.beans.factory.annotation.Autowired;
import track.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        Thread servidorThread = new Thread(() -> {
            try {
                Servidor.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String data = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
            System.out.println(data + " --- Encerrando a aplicação");
            servidorThread.interrupt();
            Log.close();
        }));
        servidorThread.start();
        Thread.sleep(1000);


        Looca looca = new Looca();
        Querys query = new Querys();
        Operacao operacoes = new Operacao();
        Scanner leitor = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("""
                    Bem-vindo ao Chefware
                    1) Capturar dados
                    2) Monitorar dados
                    3) Sair""");

            int in = leitor.nextInt();
            leitor.nextLine();
            switch (in) {
                case 1:
                    boolean capturar = true;
                    System.out.println("Computador: " + looca.getRede().getParametros().getHostName() + "\n" +
                            "\nCapturar Memória, digite m" +
                            "\nCapturar Disco, digite d" +
                            "\nCapturar Rede, digite r" +
                            "\nCapturar CPU, digite c" +
                            "\nPara sair, digite sair");

                    while (capturar) {
                        System.out.println("Digite qual informação deseja ver: ");
                        String decisao = leitor.nextLine();
                        switch (decisao) {
                            case "m", "d", "c", "r" -> {
                                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                                executor.scheduleAtFixedRate(() -> {
                                    query.operacao(decisao);
                                }, 0, 5, TimeUnit.SECONDS);
                                String inC = leitor.nextLine();
                                if (inC.equals("sair")) {
                                    executor.shutdown();
                                    System.out.println("Capturada parada com sucesso!");
                                    capturar = false;
                                }
                            }
                            case "sair" -> {
                                capturar = false;
                            }
                        }
                    }
                    break;
                case 2:
                    boolean verDados = true;
                    Log.printLog("Operação sucedida");
                    System.out.println("Computador: " + looca.getRede().getParametros().getHostName() + "\n" +
                            "\nVer Memória, digite m" +
                            "\nVer Processos, digite p" +
                            "\nVer Disco, digite d" +
                            "\nVer Rede, digite r" +
                            "\nVer CPU, digite c" +
                            "\nPara sair, digite sair");

                    while (verDados) {
                        System.out.println("Digite qual informação deseja ver: ");
                        String inD = leitor.nextLine();

                        switch (inD) {
                            case "m" -> operacoes.mostrarMemoria();
                            case "p" -> operacoes.mostrarProcessos();
                            case "c" -> operacoes.mostrarCpu();
                            case "d" -> operacoes.mostrarDisco();
                            case "r" -> operacoes.mostrarRede();
                            case "sair" -> verDados = false;
                        }
                    }
                    break;
                case 3:
                    running = false;
                    System.out.println("Programa finalizado.");
                    servidorThread.interrupt();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        }
    }
}
