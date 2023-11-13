package chefware;

import track.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        Log.printLog("Inicializando o servidor Socket");
        System.out.println("Iniciando o servidor...");
        ServerSocket serverSocket = new ServerSocket(4000);
        Log.printLog("Servidor socket iniciado com sucesso na porta 4000");
        System.out.println("Servidor iniciado com sucesso!");
        boolean rodar = true;

        while (rodar) {
            Socket socket = serverSocket.accept();
            Log.printLog("Cliente conectado");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String comando = input.readLine();

            Log.printLog("Comando recebido do cliente: " + comando);



            try {
                Thread.sleep(1000); // 1000 milissegundos = 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


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
                Log.printLog("Mensagem recebida do servidor: oi");
            } else if ("sair".equals(comando)) {
                rodar = false;
            } else {
                Log.printLog("Houve a tentativada de execução de comando, porém ele é invalido");

            }


            socket.close();

        }

        serverSocket.close();
    }
}
