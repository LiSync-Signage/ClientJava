package dao;

import conexao.ConexaoMySQL;
import models.Empresa;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class EmpresaDAO {

    public Empresa buscarEmpresa(Integer idEmpresa) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT * FROM Empresa WHERE idEmpresa = ?";

        try {
            return conSQLServer.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Empresa.class), idEmpresa);
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

    public void atualizarEmpresaLocalSQLServer (Empresa empresa) {
//        ConexaoMySQL conexao = new ConexaoMySQL();
//        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO Empresa (idEmpresa, nomeFantasia, plano) " +
//                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nomeFantasia = ?, plano = ?";

        String sqlServer = "MERGE INTO Empresa AS target\n" +
                "USING (VALUES (?, ?, ?, ?, ?)) AS source (idEmpresa, nomeFantasia, plano, novoNomeFantasia, novoPlano)\n" +
                "    ON target.idEmpresa = source.idEmpresa\n" +
                "WHEN MATCHED THEN\n" +
                "    UPDATE SET target.nomeFantasia = source.novoNomeFantasia,\n" +
                "               target.plano = source.novoPlano\n" +
                "WHEN NOT MATCHED THEN\n" +
                "    INSERT (nomeFantasia, plano)\n" +
                "    VALUES (source.nomeFantasia, source.plano);";

        try {
//            con.update(sql, empresa.getIdEmpresa(), empresa.getNomeFantasia(), empresa.getPlano().getTitulo(),
//                    empresa.getNomeFantasia(), empresa.getPlano().getTitulo());

            conSQLServer.update(sqlServer, empresa.getIdEmpresa(), empresa.getNomeFantasia(), empresa.getPlano().getTitulo(),
                    empresa.getNomeFantasia(), empresa.getPlano().getTitulo());

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

    public void atualizarEmpresaLocal (Empresa empresa) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

//        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Empresa (idEmpresa, nomeFantasia, plano) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nomeFantasia = ?, plano = ?";

//        String sqlServer = "MERGE INTO Empresa AS target\n" +
//                "USING (VALUES (?, ?, ?, ?, ?)) AS source (idEmpresa, nomeFantasia, plano, novoNomeFantasia, novoPlano)\n" +
//                "    ON target.idEmpresa = source.idEmpresa\n" +
//                "WHEN MATCHED THEN\n" +
//                "    UPDATE SET target.nomeFantasia = source.novoNomeFantasia,\n" +
//                "               target.plano = source.novoPlano\n" +
//                "WHEN NOT MATCHED THEN\n" +
//                "    INSERT (nomeFantasia, plano)\n" +
//                "    VALUES (source.nomeFantasia, source.plano);";

        try {
            con.update(sql, empresa.getIdEmpresa(), empresa.getNomeFantasia(), empresa.getPlano().getTitulo(),
                    empresa.getNomeFantasia(), empresa.getPlano().getTitulo());

//            conSQLServer.update(sqlServer, empresa.getIdEmpresa(), empresa.getNomeFantasia(), empresa.getPlano().getTitulo(),
//                    empresa.getNomeFantasia(), empresa.getPlano().getTitulo());

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

    public Integer contarPorEmpresa(Integer fkEmpresa) {
//       ConexaoMySQL conexao = new ConexaoMySQL();
//       JdbcTemplate con = conexao.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT count(*) FROM Empresa  join ambiente on empresa.idEmpresa = ambiente.fkEmpresa join televisao on ambiente.idAmbiente = televisao.fkAmbiente where fkEmpresa = ?;";

        try {
            Integer contagemTv = conSQLServer.queryForObject(sqlServer, Integer.class, fkEmpresa);
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

    public Empresa buscarPorPlano(Integer idEmpresa) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();
        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT plano FROM Empresa WHERE idEmpresa = ?";

        try {
            return conSQLServer.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Empresa.class), idEmpresa);
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
