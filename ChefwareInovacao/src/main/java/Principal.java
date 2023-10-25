import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) throws IOException {
        Scanner leitor = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(4000);
        boolean rodar = true;
        while (rodar) {
            try {
                String oi = leitor.nextLine();
                if (oi.equals("sair")){
                    rodar = false;
                }
                    Socket socket = serverSocket.accept();
                    System.out.println("Cliente conectou");
                while (true) {
                    String comando = leitor.nextLine();
                    if ("desligar".equals(comando)) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("desligar");
                    } else if ("reiniciar".equals(comando)) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("reiniciar");
                    } else if ("bloquear".equals(comando)) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("bloquear");
                    } else if ("sair".equals(comando)){
                        return;
                    } else if ("mensagem".equals(comando)){
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("mensagem");
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }
}