package dao;

import models.Televisao;
import org.LiSync.conexao.ConexaoMySQL;
import models.Componente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ComponenteDAO {
    public void registarComponenteSQLServer(Componente novoComponente) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO Componente (modelo, identificador, tipoComponente, fkTelevisao) " +
//                "VALUES (?, ?, ?, ?)";

        String sqlServer = "INSERT INTO Componente (modelo, identificador, tipoComponente, fkTelevisao) " +
                "VALUES (?, ?, ?, ?)";

        try {
//            con.update(sql, novoComponente.getModelo(), novoComponente.getIdentificador(),
//                    novoComponente.getTipoComponente(), novoComponente.getFkTelevisao());

            conSQLServer.update(sqlServer, novoComponente.getModelo(), novoComponente.getIdentificador(),
                    novoComponente.getTipoComponente(), novoComponente.getFkTelevisao());
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

    public void registarComponente(Componente novoComponente) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Componente (modelo, identificador, tipoComponente, fkTelevisao) " +
                "VALUES (?, ?, ?, ?)";

//        String sqlServer = "INSERT INTO Componente (modelo, identificador, tipoComponente, fkTelevisao) " +
//                "VALUES (?, ?, ?, ?)";

        try {
            con.update(sql, novoComponente.getModelo(), novoComponente.getIdentificador(),
                    novoComponente.getTipoComponente(), novoComponente.getFkTelevisao());

//            conSQLServer.update(sqlServer, novoComponente.getModelo(), novoComponente.getIdentificador(),
//                    novoComponente.getTipoComponente(), novoComponente.getFkTelevisao());
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




    public List<Componente> buscarComponentesPorIdTv (Integer idTelevisao) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT * FROM Componente WHERE fkTelevisao = ?";

        try {
            List<Componente> componentesLocal = conSQLServer.query(sqlServer, new BeanPropertyRowMapper<>(Componente.class), idTelevisao);
            return componentesLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Componente> buscarTipoComponentePorIdTv (String nome, Integer idTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Componente WHERE tipoComponente = ? AND fkTelevisao = ?";

        try {
            List<Componente> componentesLocal = con.query(sql, new BeanPropertyRowMapper<>(Componente.class),
                    nome, idTelevisao);
            return componentesLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Trate a exceção de fechamento da conexão local
                }
            }
        }
    }
}
