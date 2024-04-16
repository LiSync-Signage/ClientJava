package dao;

import org.LiSync.conexao.ConexaoMySQL;
import models.Televisao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class TelevisaoDAO {
    public void registrar(Televisao novaTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO Televisao (andar, setor, nome, taxaAtualizacao, hostName, fkEmpresa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con.update(sql, novaTelevisao.getAndar(), novaTelevisao.getSetor(),
                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
                    novaTelevisao.getHostName(), novaTelevisao.getFkEmpresa());
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<Televisao> buscarTelevisoesPorIdEmpresa(Integer idEmpresa) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Televisao WHERE fkEmpresa = ?";

        try {
            List<Televisao> televisoesLocal = con.query(sql, new BeanPropertyRowMapper<>(Televisao.class), idEmpresa);
            return televisoesLocal;
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

    public Televisao buscarTvPeloEndereco(String endereco) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Televisao WHERE hostName LIKE ? LIMIT 1";

        try {
            Televisao televioesLocal = con.queryForObject(sql, new BeanPropertyRowMapper<>(Televisao.class), endereco);
            return televioesLocal;

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

    // Futaramente deverá ser alterado
    public Integer contarPorEndereco(String endereco, Integer idEmpresa) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT COUNT(*) FROM Televisao JOIN Empresa ON " +
                "fkEmpresa = idEmpresa WHERE hostName = ? AND fkEmpresa = ?";

        try {
            Integer contagemTv = con.queryForObject(sql, Integer.class, endereco, idEmpresa);
            return contagemTv;

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


}
