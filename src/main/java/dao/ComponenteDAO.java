package dao;

import models.Televisao;
import conexao.ConexaoMySQL;
import models.Componente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ComponenteDAO {
    public void registarComponente(Componente novoComponente) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "INSERT INTO Componente (modelo, identificador, tipoComponente, fkTelevisao) " +
                "VALUES (?, ?, ?, ?)";

        try {
            con.update(sql, novoComponente.getModelo(), novoComponente.getIdentificador(),
                    novoComponente.getTipoComponente(), novoComponente.getFkTelevisao());
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

    private JdbcTemplate jdbcTemplate;

    public ComponenteDAO() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        this.jdbcTemplate = conexao.getconexaoMySqlLocal();
    }

    public Componente buscarComponentePorId(int id) {
        String sql = "SELECT * FROM Componente WHERE idComponente = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Componente.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Componente> buscarComponentesPorIdTv (Integer idTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getconexaoMySqlLocal();

        String sql = "SELECT * FROM Componente WHERE fkTelevisao = ?";

        try {
            List<Componente> componentesLocal = con.query(sql, new BeanPropertyRowMapper<>(Componente.class), idTelevisao);
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
