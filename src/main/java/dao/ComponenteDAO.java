package dao;

import conexao.ConexaoSQLServer;
import conexao.ConexaoSlack;
import models.*;
import conexao.ConexaoMySQL;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponenteDAO {
    public void registarComponenteSQLServer(ComponenteTv novoComponente) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
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

    public void registarComponente(ComponenteTv novoComponente) {
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

        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT * FROM Componente WHERE fkTelevisao = ?";

        List<Componente> componentes = new ArrayList<>();

        try {
            List<ComponenteTv> componentesLocal = conSQLServer.query(sqlServer, new BeanPropertyRowMapper<>(ComponenteTv.class), idTelevisao);


            for (ComponenteTv componenteDaVez : componentesLocal) {


                    if(componenteDaVez.getTipoComponente().equals("CPU")) {
                            Cpu cpu = new Cpu(componenteDaVez.getModelo(), componenteDaVez.getIdentificador(), componenteDaVez.getFkTelevisao());
                            componentes.add(cpu);
                    }else if(componenteDaVez.getTipoComponente().equals("Disco")) {
                            Disco disco = new Disco(componenteDaVez.getModelo(), componenteDaVez.getIdentificador(), componenteDaVez.getFkTelevisao());
                            componentes.add(disco);
                    }else {
                            MemoriaRam memoria = new MemoriaRam(componenteDaVez.getModelo(), componenteDaVez.getIdentificador(), componenteDaVez.getFkTelevisao());
                            componentes.add(memoria);
                    }
            }
            return componentes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ComponenteTv> buscarTipoComponentePorIdTv (String nome, Integer idTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Componente WHERE tipoComponente = ? AND fkTelevisao = ?";

        try {
            List<ComponenteTv> componentesLocal = con.query(sql, new BeanPropertyRowMapper<>(ComponenteTv.class),
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
