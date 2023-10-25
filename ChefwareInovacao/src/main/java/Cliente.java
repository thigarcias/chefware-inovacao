import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("100.25.160.62", 4000);
        InputStreamReader input = new InputStreamReader(socket.getInputStream());
        BufferedReader output = new BufferedReader(input);
        boolean rodar = true;

        while (rodar) {
            String comando = output.readLine();
            System.out.println("Comando recebido do servidor: " + comando);
            if ("desligar".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("shutdown -s -t 1");
                } catch (IOException exception) {
                    System.err.println("Erro ao executar o comando de desligamento: " + exception.getMessage());
                }
            } else if ("reiniciar".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("shutdown -r -t 0 -f");
                } catch (IOException ex) {
                    System.err.println("Erro ao executar o comando de reinicio: " + ex.getMessage());
                }
            } else if ("bloquear".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
                } catch (IOException ex) {
                    System.err.println("Erro ao executar o comando de bloqueio: " + ex.getMessage());
                }
            } else if ("mensagem".equals(comando)) {
                System.out.println("Mensagem recebida do servidor: oi");
            } else if ("sair".equals(comando)) {
                rodar = false;
            } else {
                System.out.println("O comando ta errado");
            }


        }
        socket.close();
    }
}
