package dao;

import models.Ambiente;
import models.Comando;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class ComandoDAO {

    public static void insertComando(Comando comando) {
        org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        try {
            con.update(sql, comando.getComando(), comando.getFkTelevisao());
        } catch (Exception e)  {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Trate a exceção de fechamento da conexão local
                }
            }
        }
    }
}
