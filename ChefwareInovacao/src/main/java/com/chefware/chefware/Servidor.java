package com.chefware.chefware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        boolean rodar = true;

        while (rodar) {
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectou");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String comando = input.readLine();

            System.out.println("Comando recebido do cliente: " + comando);



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
            } else if ("sair".equals(comando)) {
                rodar = false;
            } else {
                System.out.println("O comando ta errado");
            }


            socket.close();

        }

        serverSocket.close();
    }
}
