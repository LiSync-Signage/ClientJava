package dao;

import conexao.ConexaoMySQL;
import conexao.ConexaoSQLServer;
import models.Comando;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class ComandoDAO {

    public static void updateComandoSQLServer(Comando comando) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        String sqlSever = "UPDATE Comando SET resposta = ? WHERE idComando = ?";

        try {
//            con.update(sql, comando.getComando(), comando.getFkTelevisao());
            con.update(sqlSever, comando.getResposta(), comando.getIdComando());
        } catch (Exception e)  {
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

    public static void insertComandoSQLServer(Comando comando) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        String sqlSever = "INSERT INTO Comando (nomeComando, resposta, fkTelevisao)\n" +
                "VALUES (?, ?, ?);";

        try {
//            con.update(sql, comando.getComando(), comando.getFkTelevisao());
            con.update(sqlSever, comando.getResposta(), comando.getResposta(), comando.getFkTelevisao());
        } catch (Exception e)  {
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

    public static void insertComando(Comando comando) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();


        String sql = "INSERT INTO Comando (nomeComando, resposta, fkTelevisao)\n" +
                "VALUES (?, ?, ?);";

        try {
            con.update(sql,comando.getnomeComando(),comando.getResposta(), 1);
//            conSQLServer.update(sqlSever, comando.getResposta(), comando.getIdComando());
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

    public static void updateComando(Comando comando) {
          conexao.ConexaoMySQL conexaoMySQL = new conexao.ConexaoMySQL();
          JdbcTemplate con = conexaoMySQL.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Comando (nomeComando, resposta, fkTelevisao)" +
                "                VALUES (?, ?, ?);";

//        String sqlSever = "INSERT INTO Comando(nome,fkTelevisao)VALUES (?,?);";

        try {
            con.update(sql,comando.getnomeComando(), comando.getResposta(), comando.getFkTelevisao());
//            conSQLServer.update(sqlSever, comando.getComando(), comando.getFkTelevisao());
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

    public Comando selectComando(Integer idTelevisao) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

        String sqlServer = "SELECT TOP 1 Comando.idComando, Comando.nomeComando, Comando.fkTelevisao, Comando.resposta\n" +
                "FROM Comando\n" +
                "JOIN Televisao ON Comando.fkTelevisao = Televisao.idTelevisao\n" +
                "WHERE Televisao.idTelevisao = ?\n" +
                "ORDER BY Comando.idComando DESC;";

        try {
            Comando comando = con.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Comando.class), idTelevisao);
            return comando;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao trazer comando do banco");
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
