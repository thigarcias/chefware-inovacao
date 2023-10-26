import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) throws IOException {
        Scanner leitor = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(4000);
        boolean rodar = true;

        while (rodar) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectou");

                while (true) {
                    String comando = leitor.nextLine();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    if ("desligar".equals(comando)) {
                        out.println("desligar");
                    } else if ("reiniciar".equals(comando)) {
                        out.println("reiniciar");
                    } else if ("bloquear".equals(comando)) {
                        out.println("bloquear");
                    } else if ("mensagem".equals(comando)) {
                        out.println("mensagem");
                    } else if ("sair".equals(comando)) {
                        out.println("sair");
                        rodar = false;
                        break;
                    } else {
                        out.println("comando_invalido");
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        serverSocket.close();
    }
}
