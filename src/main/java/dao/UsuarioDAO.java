package dao;

import org.LiSync.conexao.ConexaoMySQL;
import models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class UsuarioDAO {

    public Integer contarUsuariosExistentes(String email, String senha) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        String  sql = "SELECT COUNT(*) FROM Usuario WHERE email = ? AND senha = ?";

        try {
            Integer countLocal = con.queryForObject(sql, Integer.class, email, senha);
            return countLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
    public Usuario buscarCreedenciasUsuario(String email, String senha) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";

        try {
            Usuario usuarioLocal = con.queryForObject(sql, new BeanPropertyRowMapper<>(Usuario.class), email, senha);
            return usuarioLocal;
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

    public void atualizarUsuarioLocal (Usuario usuario) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        org.LiSync.conexao.ConexaoSQLServer conexaoSQLServer = new org.LiSync.conexao.ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoSqlServerLocal();

        String sql = "INSERT INTO Usuario (idUsuario, nome, fkEmpresa) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nome = ?, fkEmpresa = ?";

        String sqlServer = "MERGE INTO Usuario AS target\n" +
                "USING (VALUES (?, ?, ?, ?, ?)) AS source (idUsuario, nome, fkEmpresa, novoNome, novaFkEmpresa)\n" +
                "    ON target.idUsuario = source.idUsuario\n" +
                "WHEN MATCHED THEN\n" +
                "    UPDATE SET target.nome = source.novoNome,\n" +
                "               target.fkEmpresa = source.novaFkEmpresa\n" +
                "WHEN NOT MATCHED THEN\n" +
                "    INSERT (idUsuario, nome, fkEmpresa)\n" +
                "    VALUES (source.idUsuario, source.nome, source.fkEmpresa);";

        try {
            con.update(sql, usuario.getIdUsuario(), usuario.getNome(), usuario.getFkEmpresa(),
                    usuario.getNome(), usuario.getFkEmpresa());
            conSQLServer.update(sqlServer, usuario.getIdUsuario(), usuario.getNome(), usuario.getFkEmpresa(),
                    usuario.getNome(), usuario.getFkEmpresa());
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
