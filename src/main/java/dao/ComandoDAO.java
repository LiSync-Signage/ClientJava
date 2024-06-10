package dao;

import models.Comando;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class ComandoDAO {

    public static void updateComandoSQLServer(Comando comando) {
//          org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
//          JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        String sqlSever = "UPDATE Comando SET resposta = ? WHERE idComando = ?";

        try {
//            con.update(sql, comando.getComando(), comando.getFkTelevisao());
          conSQLServer.update(sqlSever, comando.getResposta(), comando.getIdComando());
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

    public static void insertComandoSQLServer(Comando comando) {
//          org.LiSync.conexao.ConexaoMySQL conexaoMySQL = new org.LiSync.conexao.ConexaoMySQL();
//          JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO comando(nome,fkTelevisao)VALUES (?,?);";

        String sqlSever = "INSERT INTO Comando (nomeComando, resposta, fkTelevisao)\n" +
                "VALUES (?, ?, ?);";

        try {
//            con.update(sql, comando.getComando(), comando.getFkTelevisao());
            conSQLServer.update(sqlSever, comando.getResposta(), comando.getResposta(), comando.getFkTelevisao());
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

    public static void insertComando(Comando comando) {
          conexao.ConexaoMySQL conexaoMySQL = new conexao.ConexaoMySQL();
          JdbcTemplate con = conexaoMySQL.getconexaoLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();


        String sql = "INSERT INTO Comando (nomeComando, resposta, fkTelevisao)\n" +
                "VALUES (?, ?, ?);";

        try {
            con.update(sql, comando.getnomeComando(),comando.getResposta(), comando.getFkTelevisao());
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

        String sql = "UPDATE Comando SET resposta = ? WHERE idComando = ?;";

//        String sqlSever = "INSERT INTO Comando(nome,fkTelevisao)VALUES (?,?);";

        try {
            con.update(sql, comando.getResposta(), comando.getFkTelevisao());
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

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

        String sqlServer = "SELECT TOP 1 Comando.idComando, Comando.nomeComando, Comando.fkTelevisao, Comando.resposta\n" +
                "FROM Comando\n" +
                "JOIN Televisao ON Comando.fkTelevisao = Televisao.idTelevisao\n" +
                "WHERE Televisao.idTelevisao = ?\n" +
                "ORDER BY Comando.idComando DESC;";

        try {
            Comando comando = conSQLServer.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Comando.class), idTelevisao);
            return comando;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao trazer comando do banco");
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
