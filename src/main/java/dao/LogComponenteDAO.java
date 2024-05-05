package dao;

import models.Componente;
import models.LogComponente;
import models.Processo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class LogComponenteDAO {
    public LogComponenteDAO() {
    }

    public void salvarLogComponente(List<LogComponente> logComponenteList) {
        org.LiSync.conexao.ConexaoMySQL conexao = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {
            for (LogComponente logComponente : logComponenteList) {
                con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
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

    public void salvarLogComponenteIndividual(LogComponente logComponente) {
        org.LiSync.conexao.ConexaoMySQL conexao = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {

                con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());



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
