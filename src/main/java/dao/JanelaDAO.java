package dao;

import org.LiSync.conexao.ConexaoMySQL;
import models.Janela;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
public class JanelaDAO {
    public void salvarVariasJanelas(List<Janela> janelas) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();


        String sql = "INSERT INTO Janela (pidJanela, titulo, localizacao, visivel, " +
                "fkTelevisao) VALUES (?, ?, ?, ?, ?)";

        String sqlServer = "INSERT INTO Janela (pidJanela, titulo, localizacao, visivel, " +
                "fkTelevisao) VALUES (?, ?, ?, ?, ?)";

        try {
            for (Janela janela : janelas) {
                con.update(sql, janela.getPidJanela(), janela.getTitulo(),
                        janela.getLocalizacao(), janela.getVisivel(), janela.getFkTelevisao());

                conSQLServer.update(sqlServer, janela.getPidJanela(), janela.getTitulo(),
                        janela.getLocalizacao(), janela.getVisivel(), janela.getFkTelevisao());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (conSQLServer != null) {
                try {
                    conSQLServer.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null){
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
