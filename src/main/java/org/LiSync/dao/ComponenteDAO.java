package org.LiSync.dao;

import org.LiSync.conexao.ConexaoMySQL;
import org.LiSync.models.Componente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ComponenteDAO {
    public void registarComponente(Componente novoComponente) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        String sql = "INSERT INTO Componente (modelo, fkTelevisao, fkTipoComponente) VALUES (?, ?, ?)";

        try {
            con.update(sql, novoComponente.getModelo(), novoComponente.getFkTelevisao(),
                    novoComponente.getFkTipoComponente());
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

    public List<Componente> buscarComponentesPorIdTv (Integer idTelevisao) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        JdbcTemplate con = conexao.getConexaoDoBanco();

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
        JdbcTemplate con = conexao.getConexaoDoBanco();

        String sql = "SELECT * FROM Componente JOIN TipoComponente ON fkTipoComponente = idTipoComponente " +
                "WHERE nome = ? AND fkTelevisao = ?";

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
