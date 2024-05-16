import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
		String dataFormatada = getDataFormatada();
		String horaFormatada = getHoraFormatada();
		String nomePasta = "logs";
		criarPasta(nomePasta);
		criarArquivo("log-" + dataFormatada + horaFormatada + ".txt");
	}
	
	private static String getDataFormatada() {
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
		return formatador.format(data);
	}
	
	private static String getHoraFormatada(){
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("HHmm");
		return formatador.format(data);
	}
	
	private static void criarArquivo(String nomeArquivo) {
		File arquivo = new File(nomePasta + File.separator + nomeArquivo);
		try {
			if (arquivo.createNewFile()) {
				System.out.println("Arquivo criado com sucesso: " + nomeArquivo);
			} else {
				System.out.println("Arquivo já existe: " + nomeArquivo);
			}
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo: " + e.getMessage());
		}
	}
	
	private static void criarPasta(String nomePasta) {
		File pasta = new File(nomePasta);
		if (!pasta.exists()) {
			if (pasta.mkdir()) {
				System.out.println("Pasta criada com sucesso: " + nomePasta);
			} else {
				System.out.println("Erro ao criar pasta: " + nomePasta);
			}
		} else {
			System.out.println("Pasta já existe: " + nomePasta);
		}
	}
}