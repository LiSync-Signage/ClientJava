package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySQL extends Conexao{
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
        dataSource.setUsername("Ubuntu");
        dataSource.setPassword("urubu100");

        conexaoMySqlLocal = new JdbcTemplate(dataSource);
    }
    @Override
    public JdbcTemplate getconexaoLocal(){
        return conexaoMySqlLocal;
    }
}
