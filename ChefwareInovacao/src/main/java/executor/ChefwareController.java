package executor;

import chefware.Servidor;
import chefware.ServidorComandos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import track.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
public class ChefwareController {
    @PostMapping("/inovacao")
    @CrossOrigin
    public ResponseEntity<String> comando(@RequestBody ChefwareRequest comando) throws IOException {
        Socket socket = new Socket("localhost", 4000);
        Log.printLog("[SERVER] Cliente novo conectado");
        if (comando.getComando() == null || comando.getComando().isEmpty()) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Log.printLog("[ERR] [SERVER] O comando não pode ser nulo ou vazio");
            out.println("O comando não pode ser nulo ou vazio.");
            return ResponseEntity.badRequest().body("O comando ("+comando.getComando()+") não pode ser nulo ou vazio.");
        }
        if (!Servidor.isCommanndExists(comando.getComando())){
            Log.printLog("[ERR] [SERVER] Comando ("+comando.getComando()+") requisitado é inválido");
            return ResponseEntity.badRequest().body("Comando requisitado é inválido");
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(comando.getComando());
        Log.printLog("[SERVER] Comando("+comando.getComando()+") executado com sucesso!");
        return ResponseEntity.ok("Comando %s executado com sucesso!".formatted(comando.getComando()));
    }
}
