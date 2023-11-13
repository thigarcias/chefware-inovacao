package chefware;

import org.springframework.http.ResponseEntity;
import track.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void serverStart() throws IOException {
        Log.printLog("[SERVER] Inicializando o servidor Socket");
        System.out.println("[SERVER] Iniciando o servidor...");
        ServerSocket serverSocket = new ServerSocket(4000);
        Log.printLog("[SERVER] Servidor socket iniciado com sucesso na porta 4000");
        System.out.println("[SERVER] Servidor iniciado com sucesso!");

        boolean rodar = true;
        while (rodar) {
            Socket socket = serverSocket.accept();
            Log.printLog("[SERVER] Cliente conectado");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String comando = input.readLine();

            Log.printLog("[SERVER] Comando recebido do cliente: " + comando);


            try {
                Thread.sleep(1000); // 1000 milissegundos = 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if ("desligar".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("shutdown -s -t 1");
                } catch (IOException exception) {
                    System.err.println("[ERR] [SERVER] Erro ao executar o comando de desligamento: " + exception.getMessage());
                    Log.printLog("[ERR] [SERVER] Erro ao executar o comando de desligamento: " + exception.getMessage());
                }
            } else if ("reiniciar".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("shutdown -r -t 0 -f");
                } catch (IOException ex) {
                    System.err.println("[ERR] [SERVER] Erro ao executar o comando de reinicio: " + ex.getMessage());
                    Log.printLog("[ERR] [SERVER] Erro ao executar o comando de reinicio: " + ex.getMessage());

                }
            } else if ("bloquear".equals(comando)) {
                try {
                    Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
                } catch (IOException ex) {
                    System.err.println("[ERR] [SERVER] Erro ao executar o comando de bloqueio: " + ex.getMessage());
                    Log.printLog("[ERR] [SERVER] Erro ao executar o comando de bloqueio: " + ex.getMessage());
                }
            } else if ("sair".equals(comando)) {
                rodar = false;
            } else {
                Log.printLog("[ERR] [SERVER] Houve a tentativada de execução de comando, porém ele é invalido");
                System.out.println("[ERR] [SERVER] Houve a tentativada de execução de comando, porém ele é invalido");
                return;
            }
            socket.close();
        }
        serverSocket.close();
    }

    public static Boolean isCommanndExists(String comando) {
        for (ServidorComandos valorEnum : ServidorComandos.values()) {
            if (valorEnum.name().equalsIgnoreCase(comando)) {
                return true;
            }
        }
        return false;
    }
}
