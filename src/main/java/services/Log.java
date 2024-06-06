package services;

import dao.ComponenteDAO;
import models.Componente;
import models.LogComponente;
import conexao.ConexaoMySQL;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {
	private JdbcTemplate jdbcTemplate;
	private ComponenteDAO componenteDAO = new ComponenteDAO();

	public Log() {
		ConexaoMySQL conexao = new ConexaoMySQL();
		this.jdbcTemplate = conexao.getconexaoMySqlLocal();
	}

	public static void main(String[] args) {
		criarPasta();
		criarArquivo();

		List<LogComponente> componentes = buscarProcessosIniciadosNaUltimaHora();

		if (componentes != null) {
			System.out.println("Processos encontrados: " + componentes.size());
		} else {
			System.out.println("Nenhum processo encontrado");
		}
	}

	private static String getDataFormatada() {
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy.MM.dd");
		return formatador.format(data);
	}

	private static String getHoraFormatada() {
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("HH.mm");
		return formatador.format(data);
	}

	private static void criarPasta() {
		File pasta = new File(getNomePasta());
		if (!pasta.exists()) {
			if (pasta.mkdir()) {
				System.out.println("Pasta criada com sucesso: " + getNomePasta());
			} else {
				System.out.println("Erro ao criar pasta: " + getNomePasta());
			}
		} else {
			System.out.println("Pasta já existe: " + getNomePasta());
		}
	}

	private static void criarArquivo() {
		File arquivo = new File(getNomePasta() + File.separator + getNomeArquivo());
		if (!arquivo.exists()) {
			try {
				if (arquivo.createNewFile()) {
					System.out.println("Arquivo criado com sucesso: " + getNomeArquivo());
				} else {
					System.out.println("Erro ao criar arquivo: " + getNomeArquivo());
				}
			} catch (IOException e) {
				System.out.println("Erro ao criar arquivo: " + e.getMessage());
			}
		} else {
			System.out.println("Arquivo já existe: " + getNomeArquivo());
		}
	}

	public void adicionarTextoNoArquivo(List<LogComponente> componentes) {
		File arquivo = new File(getNomePasta() + File.separator + getNomeArquivo());
		try (FileWriter fw = new FileWriter(arquivo, true);
			 BufferedWriter bw = new BufferedWriter(fw)) {

			for (LogComponente logComponente : componentes ) {
				Componente componente = componenteDAO.buscarComponentePorId(logComponente.getFkComponente());
				logComponente.setComponente(componente);
				double valor = logComponente.getValor();

				if (alerta(componente, valor)) {
					bw.write(logComponente.toString());
					bw.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao adicionar texto no arquivo: " + e.getMessage());
		}
	}

	public static List<LogComponente> buscarProcessosIniciadosNaUltimaHora() {
		ConexaoMySQL conexao = new ConexaoMySQL();
		JdbcTemplate con = conexao.getconexaoMySqlLocal();
		String sql = "SELECT idLogComponente, dataHora, valor, fkComponente FROM LogComponente";
		List<LogComponente> componentes = new ArrayList<>();

		try {
			componentes = con.query(sql, new BeanPropertyRowMapper<>(LogComponente.class));
			new Log().adicionarTextoNoArquivo(componentes);
		} catch (DataAccessException e) {
			System.out.println("Erro de acesso aos dados ao buscar processos: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro desconhecido ao buscar processos: " + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.getDataSource().getConnection().close();
				} catch (SQLException e) {
					System.out.println("Erro SQL ao fechar conexão: " + e.getMessage());
				}
			}
		}
		return componentes;
	}

	private boolean alerta(Componente componente, double valor) {
		String tipo = componente.getTipoComponente();
		switch (tipo) {
			case "CPU":
				return valor >= 61 && valor <= 80;
			case "DISCO":
				return valor >= 75 && valor <= 90;
			case "RAM":
				return valor >= 31 && valor <= 60;
			default:
				return false;
		}
	}

//	private boolean critico(Componente componente, double valor) {
//		String tipo = componente.getTipoComponente();
//		switch (tipo) {
//			case "CPU":
//				return valor > 80;
//			case "DISCO":
//				return valor > 90;
//			case "RAM":
//				return valor > 60;
//			default:
//				return false;
//		}
//	}

	public static String getNomePasta() {
		return "logs";
	}

	public static String getNomeArquivo() {
		return "LogAlertas- " + getDataFormatada() + "-" + getHoraFormatada() + "hs" +  ".txt";
	}
}
