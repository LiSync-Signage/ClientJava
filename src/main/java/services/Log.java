package services;

import models.Monitoramento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static String dataAtual = getDataFormatada(); // Armazena a data atual para verificação
    private static String horaAtual = getHoraFormatada(); // Armazena a hora atual para verificação

    public static void main(String[] args) {
        criarPasta();
        criarArquivoPorDia();
        criarSubPasta();
        criarArquivoPorHora();

        Monitoramento monitoramento = new Monitoramento(java.time.LocalDateTime.now(), 45.8);
        adicionarTextoNoArquivoDia(monitoramento.toString());
        adicionarTextoNoArquivoHora(monitoramento.toString());
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

    private static void criarArquivoPorDia() {
        File arquivo = new File(getNomePasta() + File.separator + getNomeArquivoPorDia());

        try {
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo diário criado com sucesso: " + getNomeArquivoPorDia());
            } else {
                System.out.println("Arquivo diário já existe: " + getNomeArquivoPorDia());
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo diário: " + e.getMessage());
        }
    }

    private static void criarArquivoPorHora() {
        File arquivo = new File(getNomePasta() + File.separator + getNomeSubPasta() + File.separator + getNomeArquivoPorHora());

        try {
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo por hora criado com sucesso: " + getNomeArquivoPorHora());
            } else {
                System.out.println("Arquivo por hora já existe: " + getNomeArquivoPorHora());
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo por hora: " + e.getMessage());
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

    private static void criarSubPasta() {
        File subPasta = new File(getNomePasta() + File.separator + getNomeSubPasta());
        if (!subPasta.exists()) {
            if (subPasta.mkdir()) {
                System.out.println("Subpasta criada com sucesso: " + getNomeSubPasta());
            } else {
                System.out.println("Erro ao criar subpasta: " + getNomeSubPasta());
            }
        } else {
            System.out.println("Subpasta já existe: " + getNomeSubPasta());
        }
    }

    private static void adicionarTextoNoArquivoDia(String texto) {
        verificarTrocaDeArquivoPorDia();

        File arquivo = new File(getNomePasta() + File.separator + getNomeArquivoPorDia());

        if (arquivo.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                writer.write(texto);
                writer.newLine(); // Adiciona uma nova linha após o texto
                System.out.println("Texto adicionado ao arquivo diário: " + getNomeArquivoPorDia());
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo diário: " + e.getMessage());
            }
        } else {
            System.out.println("Arquivo diário não existe: " + getNomeArquivoPorDia());
        }
    }

    private static void adicionarTextoNoArquivoHora(String texto) {
        verificarTrocaDeArquivoPorHora();

        File arquivo = new File(getNomePasta() + File.separator + getNomeSubPasta() + File.separator + getNomeArquivoPorHora());

        if (arquivo.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                writer.write(texto);
                writer.newLine(); // Adiciona uma nova linha após o texto
                System.out.println("Texto adicionado ao arquivo por hora: " + getNomeArquivoPorHora());
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo por hora: " + e.getMessage());
            }
        } else {
            System.out.println("Arquivo por hora não existe: " + getNomeArquivoPorHora());
        }
    }

    private static void verificarTrocaDeArquivoPorDia() {
        String dataAtualizada = getDataFormatada();
        if (!dataAtualizada.equals(dataAtual)) {
            dataAtual = dataAtualizada;
            criarArquivoPorDia();
        }
    }

    private static void verificarTrocaDeArquivoPorHora() {
        String horaAtualizada = getHoraFormatada();
        if (!horaAtualizada.equals(horaAtual)) {
            horaAtual = horaAtualizada;
            criarArquivoPorHora();
        }
    }

   public static String getNomePasta() {
           return "logs";
       }

       public static String getNomeSubPasta() {
           return "logPorHora";
       }

       public static String getNomeArquivoPorDia() {
           return "AlertasPorDia-" + getDataFormatada() + ".txt"; // Nome do arquivo diário
       }

       public static String getNomeArquivoPorHora() {
           return "AlertasPorHora-" + getDataFormatada() + "-" + getHoraFormatada() + ".txt"; // Nome do arquivo por hora
       }
}
