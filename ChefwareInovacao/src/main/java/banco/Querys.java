package banco;

import banco.tabelas.DadosEstaticos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Querys {

    LoocaValores valores = new LoocaValores();
    String formattedDateTime;
    String user;
    String senha;

    public void dataHora() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = zonedDateTime.format(formatter);
    }

    public void operacao(String query) {
        Conexao conexao = new Conexao();
        LoocaValores valores = new LoocaValores();
        JdbcTemplate con = conexao.getConexaoDoBanco();
        dataHora();

        if (query.equals("m")) {
            // DADOS ESTATICOS
            List<DadosEstaticos> dadosEstaticos = con.query("""
                    SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                    """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
            Boolean existoNoBanco = false;
            for (DadosEstaticos dados : dadosEstaticos) {
                if (dados.getDescricao().contains("Memoria")) {
                    existoNoBanco = true;
                    break;
                }
            }
            if (!existoNoBanco) {
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Memoria Espaço Total", valores.converterValor(valores.getMemoriaTotal()), "GB", 1, 1, 1);
                System.out.println("Componentes inseridos no banco!");
            } else {
                //TODO Criar validação se o valor permanece o mesmo do looca
            }

            // DADOS DINAMICOS
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 1, 1, formattedDateTime,"Memoria em Uso", valores.converterValor(valores.getMemoriaEmUso()), "GB");
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 1, 1, formattedDateTime,"Memoria Disponível", valores.converterValor(valores.getMemoriaDisponivel()), "GB");
            System.out.println("Operação realizada com sucesso!!");
        }

        if (query.equals("d")) {
            // DADOS ESTATICOS
            List<DadosEstaticos> dadosEstaticos = con.query("""
                    SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                    """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
            Boolean existoNoBanco = false;
            for (DadosEstaticos dados : dadosEstaticos) {
                if (dados.getDescricao().equals("Disco")) {
                    existoNoBanco = true;
                    break;
                }
            }
            if (!existoNoBanco) {
                // Tamanho do Disco
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Disco Tamanho Total", valores.converterValor(valores.getDiscoTamanho()), "GB", 2, 1, 2);
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Disco Espaço Livre", valores.converterValor(valores.getDiscoLivre()), "GB", 2, 1, 2);
                System.out.println("Componentes inseridos no banco!");
            } else {
                //TODO Criar validação se o valor permanece o mesmo do looca
                System.out.println("Operação realizada com sucesso!!");
            }
        }

        if (query.equals("r")) {
            // DADOS ESTATICOS
            List<DadosEstaticos> dadosEstaticos = con.query("""
                    SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                    """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
            Boolean existoNoBanco = false;
            for (DadosEstaticos dados : dadosEstaticos) {
                if (dados.getDescricao().contains("Rede")) {
                    existoNoBanco = true;
                    break;
                }
            }
            if (!existoNoBanco) {
                // Nome da Rede
                for (int i = 0; i < valores.getRedeNome().size(); i++) {
                    con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Nome da Interface %s".formatted(valores.getRedeNome().get(i)),
                            valores.converterValor(valores.getDiscoTamanho()), null, 3, 1, 3);
                }
                //IPV4
                for (int i = 0; i < valores.getRedeIPV4().size(); i++) {
                    con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Interface %s IPV4".formatted(valores.getRedeNome().get(i)),
                            valores.getRedeIPV4().get(i).replace("[", "").replace("]", ""), null, 3, 1, 3);
                    }
                //IPV6
                for (int i = 0; i < valores.getRedeIPV6().size(); i++) {
                    con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "Rede Interface %s IPV6".formatted(valores.getRedeNome().get(i)),
                            valores.getRedeIPV6().get(i).replace("[", "").replace("]", ""), null, 3, 1, 3);
                }
                System.out.println("Componentes inseridos no banco!");

            } else {
                //TODO Criar validação se o valor permanece o mesmo do looca
                System.out.println("Operação realizada com sucesso!");
            }

            // DADOS DINAMICOS
            // Bytes Enviados e Recebidos


            for (int i = 0; i < valores.getRedeNome().size(); i++) {
                con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 3, 3, formattedDateTime,"Rede Bytes Enviados %s".formatted(valores.getRedeNome().get(i)), valores.getRedeBytesEnviados().get(i), "B");
                con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 3, 3, formattedDateTime,"Rede Bytes Recebidos %s".formatted(valores.getRedeNome().get(i)), valores.getRedeBytesRecebidos().get(i), "B");
            }

            // Pacotes Enviados e Recebidos
            for (int i = 0; i < valores.getRedeNome().size(); i++) {
                con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 3, 3, formattedDateTime,"Rede Pacotes Enviados %s".formatted(valores.getRedeNome().get(i)), valores.getRedePacotesEnviados().get(i), "B");
                con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 3, 3, formattedDateTime,"Rede Pacotes Recebidos %s".formatted(valores.getRedeNome().get(i)), valores.getRedePacotesRecebidos().get(i), "B");
            }
        }

        if (query.equals("c")) {
            // DADOS ESTATICOS
            List<DadosEstaticos> dadosEstaticos = con.query("""
                    SELECT Especificacoes.tipo, Dados.descricao, Dados.valor, Dados.unidadeMedida from Dados JOIN Especificacoes ON fkEspecificacoes = idEspecificacoes;
                    """, new BeanPropertyRowMapper<>(DadosEstaticos.class));
            Boolean existoNoBanco = false;
            for (DadosEstaticos dados : dadosEstaticos) {
                if (dados.getDescricao().contains("CPU")) {
                    existoNoBanco = true;
                }
            }
            if (!existoNoBanco) {
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Fabricante", valores.getCpuFabricante(), null, 4, 1, 4);
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Nome", valores.getCpuNome(), null, 4, 1, 4);
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Frequencia", valores.converterValor(valores.getCpuFrequencia()), "GHZ", 4, 1, 4);
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Fisicas", valores.getCpuFisicas(), null, 4, 1, 4);
                con.update("INSERT INTO Dados VALUES (?,?,?,?,?,?,?)", null, "CPU Logicas", valores.getCpuLogicas(), null, 4, 1, 4);
                System.out.println("Componentes inseridos no banco!");
            } else {
                //TODO Criar validação se o valor permanece o mesmo do looca
                System.out.println("Operação realizada com sucesso!");
            }

            // DADOS DINAMICOS
            con.update("INSERT INTO Historico VALUES (?, ?, ?, ?, ?, ?, ?, ?)", null, 1, 4, 4, formattedDateTime,"CPU em Uso", valores.converterValor(valores.getCpuUso()), "%");


        }

    }
}

