package banco;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import track.Log;

public class Conexao {
    private JdbcTemplate conexaoDoBanco;

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();
        Log.printLog("Instanciando a conexão com o MySQL");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/chefware"); // Conectar ao banco de sistema MySQL
        dataSource.setUsername("root");
        dataSource.setPassword("9090");
        conexaoDoBanco = new JdbcTemplate(dataSource);
        Log.printLog("Conectado com o banco de dados na url: "+ dataSource.getUrl());
    }
    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}
