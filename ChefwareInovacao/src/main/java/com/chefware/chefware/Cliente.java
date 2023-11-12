package com.chefware.chefware;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4000);
        Scanner leitor = new Scanner(System.in);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String comando = leitor.nextLine();
            if ("desligar".equals(comando)) {
                out.println(comando);
            } else if ("reiniciar".equals(comando)) {
                out.println(comando);
            } else if ("bloquear".equals(comando)) {
                out.println(comando);
            } else if ("mensagem".equals(comando)) {
                out.println(comando);
            } else if ("sair".equals(comando)) {
                out.println(comando);
                break;
            } else {
                out.println("comando_invalido");
            }
        }
        socket.close();
    }
}

