package services;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log{
		public static void main(String[] args) {
			criarPasta();
			criarArquivo();
			adicionarTextoNoArquivo();
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
		
		private static void criarArquivo() {
			File arquivo = new File(getNomePasta()  + File.separator + getNomeArquivo());
			
			try {
				if (arquivo.createNewFile()) {
					System.out.println("Arquivo criado com sucesso: " + getNomeArquivo());
				} else {
					System.out.println("Arquivo já existe: " + getNomeArquivo());
				}
			} catch (IOException e) {
				System.out.println("Erro ao criar arquivo: " + e.getMessage());
			}
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
		
		private static void adicionarTextoNoArquivo() {
			String texto = "Olá";
			File arquivo = new File(getNomePasta() + File.separator + getNomeArquivo());
			
			if (arquivo.exists()) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
					writer.write(texto);
					writer.newLine(); // Adiciona uma nova linha após o texto
					System.out.println("Texto adicionado ao arquivo: " + getNomeArquivo());
				} catch (IOException e) {
					System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
				}
			} else {
				System.out.println("Arquivo não existe: " + getNomeArquivo());
			}
		}
		
		public static String getNomePasta() {
			return "logs";
		}
		
		public static String getNomeArquivo() {
			return "log-" + getDataFormatada() + getHoraFormatada() + ".txt";
		}
		
		
	}
