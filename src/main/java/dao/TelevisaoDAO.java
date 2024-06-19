package dao;

import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import conexao.ConexaoMySQL;
import models.Televisao;

public class TelevisaoDAO {
    public void registrar(Televisao novaTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoLocal();

//        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Televisao (idTelevisao, nomeTelevisao, taxaAtualizacao, hostname ,fkAmbiente) " +
                "VALUES (?, ?, ?, ?, ? ) ON DUPLICATE KEY UPDATE hostname = ?;";

//        String sqlServer = "INSERT INTO Televisao (nome, taxaAtualizacao, hostName ,fkAmbiente) " +
//                "VALUES (?, ?, ?, ? )";

        try {
            System.out.println(novaTelevisao.getFkAmbiente());
            con.update(sql,1,
                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
                    novaTelevisao.getHostName(), 1,novaTelevisao.getHostName());

//            conSQLServer.update(sqlServer,
//                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
//                    novaTelevisao.getHostName(), novaTelevisao.getFkAmbiente());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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

    public void registrarSQLServer(Televisao novaTelevisao) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

//        String sql = "INSERT INTO Televisao (nome, taxaAtualizacao, hostName ,fkAmbiente) " +
//                "VALUES (?, ?, ?, ? )";

        String sqlServer = "INSERT INTO Televisao (nomeTelevisao, taxaAtualizacao, hostname ,fkAmbiente) " +
                "VALUES (?, ?, ?, ? )";

        try {
            System.out.println(novaTelevisao.getFkAmbiente());
//            con.update(sql,
//                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
//                    novaTelevisao.getHostName(), novaTelevisao.getFkAmbiente());

            conSQLServer.update(sqlServer,
                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
                    novaTelevisao.getHostName(), novaTelevisao.getFkAmbiente());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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

//    public  List<Televisao> buscarTelevisoesPorIdEmpresa(Integer idEmpresa, Integer idAmbiente) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();
//
//        String sql = "SELECT * FROM Televisao join ambiente join Empresa where idAmbiente=?  and FkEmpresa = ?;";
//
//        try {
//            List<Televisao> televisoesLocal = con.query(sql, new BeanPropertyRowMapper<>(Televisao.class),idAmbiente, idEmpresa);
//            return televisoesLocal;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (con != null) {
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public Televisao buscarTvPeloEndereco(String endereco) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

            String sqlServer = "SELECT TOP 1 * FROM Televisao WHERE hostname = ?;\n";

        try {
            Televisao televioesLocal = conSQLServer.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Televisao.class), endereco);
            return televioesLocal;

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


    // Futaramente deverá ser alterado
    public Integer contarPorEndereco(String endereco) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getconexaoLocal();

        String sqlServer = "select count(*) from Televisao where hostname = ?;";

        try {
            Integer contagemTv = conSQLServer.queryForObject(sqlServer, Integer.class, endereco);
            return contagemTv;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

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