package dao;

import models.Ambiente;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class AmbienteDAO {
    public static void insertAmbiente(Ambiente ambiente) {
        org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";
        String sqlServer = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";

        try {
            con.update(sql, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());
            conSQLServer.update(sqlServer, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());

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

    public Integer getIdpAndarSetor(String andar, String setor) {
        org.LiSync.conexao.ConexaoMySQL conexao = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT idAmbiente FROM ambiente WHERE andar = ? AND setor = ?;";

        try {
            Integer countLocal = con.queryForObject(sql, Integer.class, andar, setor);
            return countLocal;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

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
