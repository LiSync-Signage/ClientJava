package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySQL {
    private JdbcTemplate conexaoMySqlLocal;

    public ConexaoMySQL(){
        BasicDataSource dataSource = new BasicDataSource();

        /*
        Alterção para conexão SQL Server:
        com.microsoft.sqlserver.jdbc.SQLServerDriver
        jdbc:sqlserver://localhost:1433;database=mydb
        */

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/lisyncDB");
        dataSource.setUsername("root");
        dataSource.setPassword("1304");

        conexaoMySqlLocal = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getconexaoMySqlLocal(){
        return conexaoMySqlLocal;
    }
}
