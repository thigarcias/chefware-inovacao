package model;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import track.Log;

public class DatabaseConnection {
    private JdbcTemplate conexaoDoBanco;

    public DatabaseConnection() {
        BasicDataSource dataSource = new BasicDataSource();
        Log.printLog("[CHEFWARE] Instanciando a conexão com o MySQL");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/chefware"); // Conectar ao banco de sistema MySQL
        dataSource.setUsername("root");
        dataSource.setPassword("9090");
        conexaoDoBanco = new JdbcTemplate(dataSource);
        Log.printLog("[CHEFWARE] Conectado com o banco de dados na url: "+ dataSource.getUrl());
    }
    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}
