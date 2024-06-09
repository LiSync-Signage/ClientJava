package dao;

import conexao.ConexaoMySQL;
import models.Processo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ProcessoDAO {
//    public void salvarProcesso(Processo processo) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();
//
////        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
////        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();
//
//        String sql = "INSERT INTO Processo (pid, nome, dataHora, fkTelevisao) VALUES (?, ?, ?, ?)";
////        String sqlServer = "INSERT INTO LogProcesso (pid, nome, dataHora, fkTelevisao) VALUES (?, ?, ?, ?)";
//
//        try {
//            con.update(sql, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getFkTelevisao());
////            conSQLServer.update(sqlServer, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getFkTelevisao());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
////            if (conSQLServer != null) {
////                try {
////                    conSQLServer.getDataSource().getConnection().close();
////                } catch (SQLException e) {
////                    e.printStackTrace();
////                }
////            }
//            if (con != null){
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }

    public void salvarVariosProcessos(List<Processo> processos) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO LogProcesso (pid, nomeProcesso, dataHora, fkComponente, valor) VALUES (?, ?, ?, ?, ?)";
//        String sqlServer = "INSERT INTO LogProcesso (pid, nomeProcesso, dataHora, fkComponente, valor) VALUES (?, ?, ?, ?, ?)";

        try {
            for (Processo processo : processos) {
                con.update(sql, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getIdComponente(), processo.getValor());
//                conSQLServer.update(sqlServer, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getIdComponente(), processo.getValor());
            }

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
            if (con != null){
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void salvarVariosProcessosSQLServer(List<Processo> processos) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO Log (pid, nomeProcesso, dataHora, fkComponente, valor) VALUES (?, ?, ?, ?, ?)";
        String sqlServer = "INSERT INTO LogProcesso (pid, nomeProcesso, dataHora, fkComponente, valor) VALUES (?, ?, ?, ?, ?)";

        try {
            for (Processo processo : processos) {
//                con.update(sql, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getIdComponente(), processo.getValor());
                conSQLServer.update(sqlServer, processo.getPid(), processo.getNome(), processo.getDataHora(), processo.getIdComponente(), processo.getValor());
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
//            if (con != null){
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }




//    public Integer numeroDeProcessos() {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();
//
//        String sql = "SELECT COUNT(idProcesso) FROM Processo";
//
//        try {
//            return con.queryForObject(sql, Integer.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            if (con != null){
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
}
