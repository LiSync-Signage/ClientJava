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


        String sql = "INSERT INTO Janela (pidJanela, comando, titulo, localizacao, visivel, " +
                "fkTelevisao, dataHora) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            for (Janela janela : janelas) {
                con.update(sql, janela.getPidJanela(), janela.getComando(), janela.getTitulo(),
                        janela.getLocalizacao(), janela.getVisivel(), janela.getFkTelevisao(), janela.getDataHora());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (con != null) {
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
