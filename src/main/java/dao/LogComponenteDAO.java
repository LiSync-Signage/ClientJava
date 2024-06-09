package dao;

import models.Componente;
import models.LogComponente;
import models.Processo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import conexao.ConexaoMySQL;

public class LogComponenteDAO {
    public LogComponenteDAO() {
    }

    public void salvarLogComponente(List<LogComponente> logComponenteList) {
        conexao.ConexaoMySQL conexao = new  conexao.ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

//        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {
            for (LogComponente logComponente : logComponenteList) {
                con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());

//                conSQLServer.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
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


    public void salvarLogComponenteSQLServer(List<LogComponente> logComponenteList) {
//        org.LiSync.conexao.ConexaoMySQL conexao = new org.LiSync.conexao.ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {
            for (LogComponente logComponente : logComponenteList) {
//                con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());

                conSQLServer.update(sqlServer, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
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








    public void salvarLogComponenteIndividual(LogComponente logComponente) {
        conexao.ConexaoMySQL conexao = new conexao.ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

//        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

//        String sqlServer = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {
            con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
//            conSQLServer.update(sqlServer, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
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

    public void salvarLogComponenteIndividualSQLServer(LogComponente logComponente) {
//        org.LiSync.conexao.ConexaoMySQL conexao = new org.LiSync.conexao.ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        String sqlServer = "INSERT INTO LogComponente ( dataHora, fkComponente, valor) VALUES ( ?, ?, ? );";

        try {
//            con.update(sql, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
            conSQLServer.update(sqlServer, logComponente.getDataHora(), logComponente.getFkComponente(), logComponente.getValor());
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
}
