package org.LiSync.dao;

import org.LiSync.conexao.ConexaoMySQL;
import org.LiSync.models.Televisao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class TelevisaoDAO {
    public void registrar(Televisao novaTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        String sql = "INSERT INTO Televisao (andar, setor, taxaAtualizacao, ipTv, sistemaOperacional, fkEmpresa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con.update(sql, novaTelevisao.getAndar(), novaTelevisao.getSetor(),
                    novaTelevisao.getTaxaAtualizacao(), novaTelevisao.getIpTv(),
                    novaTelevisao.getSistemaOperacional(), novaTelevisao.getFkEmpresa());
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
        JdbcTemplate con = conexao.getConexaoDoBanco();

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

    public Televisao buscarTvPeloIpTv(String ipTv) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        String sql = "SELECT * FROM Televisao WHERE ipTv LIKE ? LIMIT 1";

        try {
            Televisao televioesLocal = con.queryForObject(sql, new BeanPropertyRowMapper<>(Televisao.class), ipTv);
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

    public Integer contarIpTv(String ipTv) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        String sql = "SELECT COUNT(*) FROM Televisao WHERE ipTv = ?";

        try {
            Integer contagemTv = con.queryForObject(sql, Integer.class, ipTv);
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
