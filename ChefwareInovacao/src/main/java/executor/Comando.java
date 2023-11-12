package executor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
public class Comando {
    @PostMapping("/inovacao")
    public ResponseEntity<String> bloquear(@RequestBody Chefware comando) throws IOException {
        Socket socket = new Socket("localhost", 4000);
        if (comando.getComando() == null || comando.isEmpty()) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("O comando não pode ser nulo ou vazio.");
        }
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(comando.getComando());
        return ResponseEntity.ok("Comando %s executado com sucessos!".formatted(comando.getComando()));
    }
}


