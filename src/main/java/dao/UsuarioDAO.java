package dao;

import conexao.ConexaoMySQL;
import models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class UsuarioDAO {

    public Integer contarUsuariosExistentes(String email, String senha) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String  sqlServer = "SELECT COUNT(*) FROM Usuario WHERE email = ? AND senha = ?";

        try {
            Integer countLocal = conSQLServer.queryForObject(sqlServer, Integer.class, email, senha);
            return countLocal;
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
    public Usuario buscarCreedenciasUsuario(String email, String senha) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sqlServer = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";

        try {
            Usuario usuarioLocal = conSQLServer.queryForObject(sqlServer, new BeanPropertyRowMapper<>(Usuario.class), email, senha);
            return usuarioLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conSQLServer != null) {
                try {
                    conSQLServer.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Trate a exceção de fechamento da conexão local
                }
            }
        }
    }

    public void atualizarUsuarioLocal (Usuario usuario) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

//        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
//        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Usuario (idUsuario, nomeUsuario, fkEmpresa) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nomeUsuario = ?, fkEmpresa = ?";

//        String sqlServer = "MERGE INTO Usuario AS target\n" +
//                "USING (VALUES (?, ?, ?, ?, ?)) AS source (idUsuario, nome, fkEmpresa, novoNome, novaFkEmpresa)\n" +
//                "    ON target.idUsuario = source.idUsuario\n" +
//                "WHEN MATCHED THEN\n" +
//                "    UPDATE SET target.nome = source.novoNome,\n" +
//                "               target.fkEmpresa = source.novaFkEmpresa\n" +
//                "WHEN NOT MATCHED THEN\n" +
//                "    INSERT (idUsuario, nome, fkEmpresa)\n" +
//                "    VALUES (source.idUsuario, source.nome, source.fkEmpresa);";

        try {
            con.update(sql, usuario.getIdUsuario(), usuario.getNomeUsuario(), usuario.getFkEmpresa(),
                    usuario.getNomeUsuario(), usuario.getFkEmpresa());
//            conSQLServer.update(sqlServer, usuario.getIdUsuario(), usuario.getNome(), usuario.getFkEmpresa(),
//                    usuario.getNome(), usuario.getFkEmpresa());
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
            if (con != null) {
                try {
                    con.getDataSource().getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Trate a exceção de fechamento da conexão local
                }
            }
        }
    }


    public void atualizarUsuarioLocalSQLServer (Usuario usuario) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        conexao.ConexaoSQLServer conexaoSQLServer = new conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

//        String sql = "INSERT INTO Usuario (idUsuario, nome, fkEmpresa) " +
//                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nome = ?, fkEmpresa = ?";

        String sqlServer =  "MERGE INTO Usuario AS target " +
                "USING (VALUES (?, ?, ?)) AS source (idUsuario, nomeUsuario, fkEmpresa) " +
                "ON target.idUsuario = source.idUsuario " +
                "WHEN MATCHED THEN " +
                "UPDATE SET target.nomeUsuario = source.nomeUsuario, target.fkEmpresa = source.fkEmpresa " +
                "WHEN NOT MATCHED THEN " +
                "INSERT (nomeUsuario, fkEmpresa) " +  // Removed idUsuario from INSERT columns
                "VALUES (source.nomeUsuario, source.fkEmpresa);";

        try {
//            con.update(sql, usuario.getIdUsuario(), usuario.getNome(), usuario.getFkEmpresa(),
//                    usuario.getNome(), usuario.getFkEmpresa());
            conSQLServer.update(sqlServer, usuario.getIdUsuario(), usuario.getNomeUsuario(), usuario.getFkEmpresa());
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
//            if (con != null) {
//                try {
//                    con.getDataSource().getConnection().close();
//                } catch (SQLException e) {
//                    e.printStackTrace(); // Trate a exceção de fechamento da conexão local
//                }
//            }
        }
    }
}
