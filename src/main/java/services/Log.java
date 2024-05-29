package services;

import models.Processo;

import conexao.ConexaoMySQL;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class  Log {
    public static void main(String[] args) {
				criarPasta();
				criarArquivo();
				List<Processo> processos = buscarProcessosIniciadosNaUltimaHora();
				
				if (processos != null) {
					System.out.println("Processos encontrados: " + processos.size());
				} else {
					System.out.println("Nenhum processo encontrado");
				}
	    
	      System.out.println(buscarProcessosIniciadosNaUltimaHora());
    }
		

    private static String getDataFormatada() {
			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
			return formatador.format(data);
		}

    private static String getHoraFormatada() {
    	Date data = new Date();
    	SimpleDateFormat formatador = new SimpleDateFormat("HHmm");
    	return formatador.format(data);
		}

    private static void criarPasta() {
      File pasta = new File(getNomePasta());
      if (!pasta.exists()){
	      if (pasta.mkdir()){
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
	
	public static void adicionarTextoNoArquivo(List<Processo> processos) {
		File arquivo = new File(getNomePasta() + File.separator + getNomeArquivo());
		try {
			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Processo processo : processos) {
				bw.write(processo.toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Erro ao adicionar texto no arquivo: " + e.getMessage());
		}
	}

public static List<Processo> buscarProcessosIniciadosNaUltimaHora() {
	ConexaoMySQL conexao = new ConexaoMySQL();
	JdbcTemplate con = conexao.getconexaoMySqlLocal();
	
	String sql = "SELECT * FROM Log";
	
	try {
		List<Processo> processos = con.query(sql, new BeanPropertyRowMapper<>(Processo.class));
		adicionarTextoNoArquivo(processos);
		return processos;
		
	} catch (Exception e) {
		System.out.println("Erro ao buscar processos: " + e.getMessage());
		return null;
		
	} finally {
		if (con != null) {
			try {
				con.getDataSource().getConnection().close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar conexão: " + e.getMessage());
			}
		}
	}
}


public static String getNomePasta() {
		return "logs";
	}

  public static String getNomeArquivo() {
     return "log-" + getDataFormatada() + "-" + getHoraFormatada() + ".txt";
  }
}
