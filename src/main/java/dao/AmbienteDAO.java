package dao;

import models.Ambiente;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class AmbienteDAO {
    public static void insertAmbienteSQLServer(Ambiente ambiente) {
//        org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

          conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
          JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";
      String sqlServer = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";

        try {
//            con.update(sql, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());
            conSQLServer.update(sqlServer, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());

        } catch (Exception e)  {
            e.printStackTrace();
        } finally {

            if (conSQLServer != null) {
                try {
                    conSQLServer.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//            if (con != null){
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    public static void insertAmbiente(Ambiente ambiente) {
        org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";
//        String sqlServer = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";

        try {
            con.update(sql, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());
//            conSQLServer.update(sqlServer, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());

        } catch (Exception e)  {
            e.printStackTrace();
        } finally {

//            if (conSQLServer != null) {
//                try {
//                    conSQLServer.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            if (con != null){
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer getIdpAndarSetor(String andar, String setor) {
        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT idAmbiente FROM Ambiente WHERE andar = ? AND setor = ?;";

        try {
            Integer countLocal = conSQLServer.queryForObject(sqlServer, Integer.class, andar, setor);
            return countLocal;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (conSQLServer != null) {
                try {
                    conSQLServer.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
