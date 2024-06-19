package dao;

import conexao.ConexaoMySQL;
import models.Ambiente;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class AmbienteDAO {
    public static void insertAmbienteSQLServer(Ambiente ambiente) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";
        String sqlServer = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";

        try {
//            con.update(sql, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());
            con.update(sqlServer, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());

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
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?) ON DUPLICATE KEY UPDATE setor = ?, andar = ? , fkEmpresa = ?;";
//        String sqlServer = "INSERT INTO Ambiente(setor,andar,fkEmpresa)VALUES (?,?,?);";

        try {
            con.update(sql, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa(),
                    ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());
//            conSQLServer.update(sqlServer, ambiente.getSetor(), ambiente.getAndar(), ambiente.getFkEmpresa());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

//            if (conSQLServer != null) {
//                try {
//                    conSQLServer.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            if (con != null) {
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer getIdpAndarSetor(String andar, String setor) {
//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoLocal();

        String sqlServer = "SELECT idAmbiente FROM Ambiente WHERE andar = ? AND setor = ?;";

        try {
            Integer countLocal = con.queryForObject(sqlServer, Integer.class, andar, setor);
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

    public List<Ambiente> getListaAmbientes(Integer idEmpresa) {
//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoLocal();

        String sql = "SELECT * FROM Ambiente WHERE fkEmpresa = ?";

        try {
            List<Ambiente> ambientes = con.query(sql, new BeanPropertyRowMapper<>(Ambiente.class), idEmpresa);
            return ambientes;
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
