package model;

import com.github.britooo.looca.api.core.Looca;
import model.tabelas.DadosEstaticos;
import model.tabelas.Maquina;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import track.Log;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Querys {

    LoocaValores valores = new LoocaValores();
    String formattedDateTime;

    Integer idMaquina;

    public void dataHora() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = zonedDateTime.format(formatter);
    }

    public void inserirMaquina(JdbcTemplate con){
        String hostname = new Looca().getRede().getParametros().getHostName();
        Boolean existeNoBanco = false;
        List<Maquina> maquinas = con.query("""
               SELECT id, nome FROM Maquina;
                """, new BeanPropertyRowMapper<>(Maquina.class));

        for (Maquina m: maquinas){
            if (m.getNome().equals(hostname)){
                existeNoBanco = true;
                idMaquina = m.getId();
            }
        }

        if (!existeNoBanco){
            Log.printLog("[ERR] [CHEFWARE] A maquina atual não esta cadastrada no sistema, cadastre-a antes de iniciar a aplicação!");
            System.out.println("A maquina atual não esta cadastrada no sistema, cadastre-a antes de iniciar a aplicação!");
            System.exit(0);
        }
    }

    public void operacao() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        LoocaValores valores = new LoocaValores();
        JdbcTemplate con = databaseConnection.getConexaoDoBanco();
        dataHora();
        inserirMaquina(con);

        // ---------------------  MEMORIA ---------------------
        memoria(con);

        // ---------------------  DISCO ---------------------
        disco(con);

        // ---------------------  REDE ---------------------
        rede(con);

        // ---------------------  CPU ---------------------
        cpu(con);
    }


    public void memoria(JdbcTemplate con) {
        Log.printLog("[MEMORIA] Realizando a operação: MEMÓRIA");
        // DADOS ESTATICOS
        Log.printLog("[MEMORIA] Select de validação na tabela DadosEstaticos");
        List<DadosEstaticos> dadosEstaticos = con.query("""
                SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
        Boolean existoNoBanco = false;
        Log.printLog("[MEMORIA] Iniciando a validação da existência do dado estático no Banco");
        for (DadosEstaticos dados : dadosEstaticos) {
            if (dados.getDescricao().contains("Memoria")) {
                existoNoBanco = true;
                break;
            }
        }
        Log.printLog("[MEMORIA] Validação encerrada: " + existoNoBanco);
        if (!existoNoBanco) {
            Log.printLog("[MEMORIA] Inserindo os dados estaticos no banco de dados");
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Memoria Espaço Total", valores.converterValor(valores.getMemoriaTotal()), "GB", 1, idMaquina, 1);
            Log.printLog("[MEMORIA] Dados estaticos inseridos com sucesso!");
            System.out.println("[MEMORIA] Componentes inseridos no banco!");
        } else {
            //TODO Criar validação se o valor permanece o mesmo do looca
        }

        // DADOS DINAMICOS
        Log.printLog("[MEMORIA] Inserindo os dados dinamicos no banco de dados");
        con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 1, 1, formattedDateTime, "Memoria em Uso", valores.converterValor(valores.getMemoriaEmUso()), "GB");
        con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 1, 1, formattedDateTime, "Memoria Disponível", valores.converterValor(valores.getMemoriaDisponivel()), "GB");
        Log.printLog("[MEMORIA] Dados dinamicos inseridos com sucesso!");
        System.out.println("[MEMORIA] Operação realizada com sucesso!!");
    }

    public void disco(JdbcTemplate con) {
        // DADOS ESTATICOS
        Log.printLog("[DISCO] Realizando a operação: DISCO");
        Log.printLog("[DISCO] Select de validação na tabela DadosEstaticos");
        List<DadosEstaticos> dadosEstaticos = con.query("""
                SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
        Boolean existoNoBanco = false;
        Log.printLog("[DISCO] Iniciando a validação da existência do dado estático no Banco");
        for (DadosEstaticos dados : dadosEstaticos) {
            if (dados.getDescricao().equals("Disco")) {
                existoNoBanco = true;
                break;
            }
        }
        if (!existoNoBanco) {
            Log.printLog("[DISCO] Inserindo os dados estaticos no banco de dados");
            // Tamanho do Disco
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Disco Tamanho Total", valores.converterValor(valores.getDiscoTamanho()), "GB", 2, idMaquina, 2);
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Disco Espaço Livre", valores.converterValor(valores.getDiscoLivre()), "GB", 2, idMaquina, 2);
            Log.printLog("[DISCO] Dados estaticos inseridos com sucesso!");
            System.out.println("[DISCO] Componentes inseridos no banco!");
        } else {
            //TODO Criar validação se o valor permanece o mesmo do looca
            Log.printLog("[DISCO] Dados ja existem no banco");
            System.out.println("[DISCO] Operação realizada com sucesso!!");
        }
    }

    public void rede(JdbcTemplate con) {
        // DADOS ESTATICOS
        Log.printLog("[REDE] Realizando a operação: REDE");
        Log.printLog("[REDE] Select de validação na tabela DadosEstaticos");
        List<DadosEstaticos> dadosEstaticos = con.query("""
                SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
        Boolean existoNoBanco = false;
        Log.printLog("[REDE] Iniciando a validação da existência do dado estático no Banco");
        for (DadosEstaticos dados : dadosEstaticos) {
            if (dados.getDescricao().contains("Rede")) {
                existoNoBanco = true;
                break;
            }
        }
        if (!existoNoBanco) {
            Log.printLog("[REDE] Inserindo os dados estaticos no banco de dados");
            // Nome da Rede
            for (int i = 0; i < valores.getRedeNome().size(); i++) {
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Nome da Interface %s".formatted(valores.getRedeNome().get(i)),
                        valores.converterValor(valores.getDiscoTamanho()), null, 3, idMaquina, 3);
            }
            //IPV4
            for (int i = 0; i < valores.getRedeIPV4().size(); i++) {
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Interface %s IPV4".formatted(valores.getRedeNome().get(i)),
                        valores.getRedeIPV4().get(i).replace("[", "").replace("]", ""), null, 3, idMaquina, 3);
            }
            //IPV6
            for (int i = 0; i < valores.getRedeIPV6().size(); i++) {
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Interface %s IPV6".formatted(valores.getRedeNome().get(i)),
                        valores.getRedeIPV6().get(i).replace("[", "").replace("]", ""), null, 3, idMaquina, 3);
            }
            Log.printLog("[REDE] Dados estaticos inseridos com sucesso!");
            System.out.println("[REDE] Componentes inseridos no banco!");

        } else {
            //TODO Criar validação se o valor permanece o mesmo do looca
            Log.printLog("[REDE] Dados ja existem no banco");
            System.out.println("[REDE] Operação realizada com sucesso!");
        }

        // DADOS DINAMICOS
        // Bytes Enviados e Recebidos
        Log.printLog("[REDE] Inserindo os dados dinamicos no banco de dados");
        for (int i = 0; i < valores.getRedeNome().size(); i++) {
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 3, 3, formattedDateTime, "Rede Bytes Enviados %s".formatted(valores.getRedeNome().get(i)), valores.getRedeBytesEnviados().get(i), "B");
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 3, 3, formattedDateTime, "Rede Bytes Recebidos %s".formatted(valores.getRedeNome().get(i)), valores.getRedeBytesRecebidos().get(i), "B");
        }

        // Pacotes Enviados e Recebidos
        for (int i = 0; i < valores.getRedeNome().size(); i++) {
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 3, 3, formattedDateTime, "Rede Pacotes Enviados %s".formatted(valores.getRedeNome().get(i)), valores.getRedePacotesEnviados().get(i), "B");
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 3, 3, formattedDateTime, "Rede Pacotes Recebidos %s".formatted(valores.getRedeNome().get(i)), valores.getRedePacotesRecebidos().get(i), "B");
        }
        Log.printLog("[REDE] Dados dinamicos inseridos com sucesso!");

    }

    public void cpu(JdbcTemplate con) {
        Log.printLog("[CPU] Realizando a operação: CPU");
        Log.printLog("[CPU] Select de validação na tabela DadosEstaticos");
        // DADOS ESTATICOS
        List<DadosEstaticos> dadosEstaticos = con.query("""
                SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
        Boolean existoNoBanco = false;
        Log.printLog("[CPU] Iniciando a validação da existência do dado estático no Banco");
        for (DadosEstaticos dados : dadosEstaticos) {
            if (dados.getDescricao().contains("CPU")) {
                existoNoBanco = true;
            }
        }
        if (!existoNoBanco) {
            Log.printLog("[CPU] Inserindo os dados estaticos no banco de dados");
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Fabricante", valores.getCpuFabricante(), null, 4, idMaquina, 4);
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Nome", valores.getCpuNome(), null, 4, 1, 4);
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Frequencia", valores.converterValor(valores.getCpuFrequencia()), "GHZ", 4, idMaquina, 4);
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Fisicas", valores.getCpuFisicas(), null, 4, idMaquina, 4);
            con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Logicas", valores.getCpuLogicas(), null, 4, idMaquina, 4);
            System.out.println("[CPU] Componentes inseridos no banco!");
            Log.printLog("[CPU] Dados estaticos inseridos com sucesso!");
        } else {
            //TODO Criar validação se o valor permanece o mesmo do looca
            Log.printLog("[CPU] Dados ja existem no banco");
            System.out.println("[CPU] Operação realizada com sucesso!");
        }

        // DADOS DINAMICOS
        Log.printLog("[CPU] Inserindo os dados dinamicos no banco de dados");
        con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, idMaquina, 4, 4, formattedDateTime, "CPU em Uso", valores.converterValor(valores.getCpuUso()), "%");
        Log.printLog("[CPU] Dados dinamicos inseridos com sucesso!");

    }
}



