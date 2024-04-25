package dao;

import org.LiSync.conexao.ConexaoMySQL;
import models.Empresa;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class EmpresaDAO {

    public Empresa buscarEmpresa(Integer idEmpresa) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate con = conexaoMySQL.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Empresa WHERE idEmpresa = ?";

        try {
            return con.queryForObject(sql, new BeanPropertyRowMapper<>(Empresa.class), idEmpresa);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void atualizarEmpresaLocal (Empresa empresa) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO Empresa (idEmpresa, nomeFantasia, plano) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nomeFantasia = ?, plano = ?";

        try {
            con.update(sql, empresa.getIdEmpresa(), empresa.getNomeFantasia(), empresa.getPlano(),
                    empresa.getNomeFantasia(), empresa.getPlano());
        } catch (Exception e) {
            e.printStackTrace();
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
