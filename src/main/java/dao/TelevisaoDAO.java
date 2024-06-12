package dao;

import conexao.ConexaoMySQL;
import models.Televisao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class TelevisaoDAO {
    public void registrar(Televisao novaTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO Televisao (nomeTelevisao, taxaAtualizacao, hostName ,fkAmbiente) " +
                "VALUES (?, ?, ?, ? )";

        try {
            System.out.println(novaTelevisao.getFkAmbiente());
            con.update(sql,
                    novaTelevisao.getNome(), novaTelevisao.getTaxaAtualizacao(),
                    novaTelevisao.getHostName(), novaTelevisao.getFkAmbiente());
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

    public  List<Televisao> buscarTelevisoesPorIdEmpresa(Integer idEmpresa, Integer idAmbiente) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Televisao join ambiente join Empresa where idAmbiente=?  and FkEmpresa = ?;";

        try {
            List<Televisao> televisoesLocal = con.query(sql, new BeanPropertyRowMapper<>(Televisao.class),idAmbiente, idEmpresa);
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

            String sql = "SELECT * FROM Televisao WHERE hostName = ? LIMIT 1";

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
    public Integer contarPorEndereco(String endereco) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "select count(*) from Televisao where hostName = ?;";

        try {
            Integer contagemTv = con.queryForObject(sql, Integer.class, endereco);
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
