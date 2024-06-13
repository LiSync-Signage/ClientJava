package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.TimeUnit;


public class ConexaoSQLServer extends Conexao {

    private JdbcTemplate conexaoSqlServerLocal;

    public ConexaoSQLServer() {
        try {
            BasicDataSource dataSource = new BasicDataSource();

            // Configuração da conexão com SQL Server usando autenticação do SQL Server
            dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            dataSource.setUrl("jdbc:sqlserver://52.6.118.102:1433;databaseName=teste;encrypt=true;trustServerCertificate=true;loginTimeout=20;");

            dataSource.setUrl("jdbc:sqlserver://23.22.35.41:1433;databaseName=teste;encrypt=true;trustServerCertificate=true;loginTimeout=20;");
            dataSource.setUsername("sa"); // Substitua pelo seu nome de usuário
            dataSource.setPassword("urubu100");   // Substitua pela sua senha

            conexaoSqlServerLocal = new JdbcTemplate(dataSource);

            // Testando a conexão
            testConnection();

        } catch (Exception e) {
            System.err.println("Erro ao configurar a conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public JdbcTemplate getconexaoLocal() {
        return conexaoSqlServerLocal;
    }

    private void testConnection() {
        try {
            conexaoSqlServerLocal.execute("SELECT 1");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados principal: \n " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ConexaoSQLServer();
    }
}