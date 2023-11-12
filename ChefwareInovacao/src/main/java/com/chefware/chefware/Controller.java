package com.chefware.chefware;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
public class Controller {
    @PostMapping("/inovacao")
    public ResponseEntity<String> bloquear(@RequestBody String comando) throws IOException {
        System.out.println("OII");
        System.out.println(comando);
        Socket socket = new Socket("localhost", 4000);
        if (comando == null || comando.isEmpty()) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("O comando não pode ser nulo ou vazio.");
        }
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(comando);


        return ResponseEntity.ok("Comando teste");
    }
}


