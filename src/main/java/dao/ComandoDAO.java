package dao;

import models.Ambiente;
import models.Comando;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class ComandoDAO {

    public static void insertComando(Comando comando) {
        org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        String sqlSever = "INSERT INTO Comando(nome,fkTelevisao)VALUES (?,?);";

        try {
            con.update(sql, comando.getComando(), comando.getFkTelevisao());
            conSQLServer.update(sqlSever, comando.getComando(), comando.getFkTelevisao());
        } catch (Exception e)  {
            e.printStackTrace();
        } finally {
            if (conSQLServer != null|| con != null) {
                try {
                    conSQLServer.getDataSource().getConnection().close();
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
