package services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import dao.ComandoDAO;
import dao.ComponenteDAO;
import dao.ProcessoDAO;
import dao.TelevisaoDAO;
import models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Monitoramento {
    public void monitorarTelevisao() {


        ServicosLisync servicosLisync = new ServicosLisync();
        Looca looca = new Looca();
        TelevisaoDAO televisaoDAO = new TelevisaoDAO();
        ComponenteDAO componenteDAO = new ComponenteDAO();
        String hostName = looca.getRede().getParametros().getHostName();


        Televisao televisao = televisaoDAO.buscarTvPeloEndereco(hostName);


        String logRegistroComponentes = "";
        List<Componente> componentes = componenteDAO.buscarComponentesPorIdTv(televisao.getIdTelevisao());



        for (Componente componenteAtual : componentes) {

            logRegistroComponentes = """
                    |----------- Componente -----------|
                    Tipo do componente: %s;
                    Modelo: %s;
                    Identificador: %s;
                    Id da Televisão: %d;
                    """.formatted(componenteAtual.getTipoComponente(), componenteAtual.getModelo(),
                    componenteAtual.getIdentificador(), componenteAtual.getFkTelevisao());
            System.out.println(logRegistroComponentes);
        }
        // Processos
        Timer timer = new Timer();
        int inicio = 0;
        int intervalo = televisao.getTaxaAtualizacao();

        List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

        List<Processo> maioresProcessosRam = new ArrayList<>();

        List<models.Processo> processoModels = new ArrayList<>();

        List<models.Processo> processoModelsMySQL = new ArrayList<>();

        List<Processo> maioresProcessosCPU = new ArrayList<>();

        Integer idComponenteCPU = componenteDAO.buscarTipoComponentePorIdTvSQLServer("CPU", televisao.getIdTelevisao()).get(0).getIdComponente();
        Integer idComponenteRAM = componenteDAO.buscarTipoComponentePorIdTvSQLServer("RAM", televisao.getIdTelevisao()).get(0).getIdComponente();

        System.out.println(idComponenteCPU + "ID COMPONENTE ");

        Boolean existeComponenteRam = componenteDAO.buscarTipoComponentePorIdTvSQLServer("RAM", televisao.getIdTelevisao()).isEmpty();
        Boolean existeComponenteCPU = componenteDAO.buscarTipoComponentePorIdTvSQLServer("CPU", televisao.getIdTelevisao()).isEmpty();




        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    maioresProcessosRam.clear();
                    maioresProcessosCPU.clear();

                    Collections.sort(processos, Comparator.comparingDouble(Processo::getUsoMemoria).reversed());

                    for (int i = 0; i < Math.min(processos.size(), 6); i++) {
                        maioresProcessosRam.add(processos.get(i));
                    }

                    Collections.sort(processos, Comparator.comparingDouble(Processo::getUsoCpu).reversed());

                    for (int i = 0; i < Math.min(processos.size(), 6); i++) {
                        maioresProcessosCPU.add(processos.get(i));
                    }

                    System.out.println("Maiores processos em uso de memoria RAM%");
                    for (int i = 0; i < maioresProcessosRam.size(); i++) {
                        Processo processoAtual = maioresProcessosRam.get(i);

                        System.out.println("""
                                |----------- Processo %d -----------|
                                Nome: %s
                                Pid: %d
                                CPU: %.2f
                                Memória: %.2f
                                 """.formatted(i, processoAtual.getNome(), processoAtual.getPid(),
                                processoAtual.getUsoCpu(), processoAtual.getUsoMemoria()));

                        if (!existeComponenteRam) {
                            processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, idComponenteRAM, processoAtual.getUsoMemoria()));
                            processoModelsMySQL.add(servicosLisync.monitoramentoProcesso(processoAtual, 3, processoAtual.getUsoMemoria()));
                        }
                    }



                    System.out.println("Maiores processos em uso de CPU%");
                    for (int i = 0; i < maioresProcessosCPU.size(); i++) {
                        Processo processoAtual = maioresProcessosCPU.get(i);

                        System.out.println("""
                                |----------- Processo %d -----------|
                                Nome: %s
                                Pid: %d
                                CPU: %.2f
                                Memória: %.2f
                                 """.formatted(i, processoAtual.getNome(), processoAtual.getPid(),
                                processoAtual.getUsoCpu(), processoAtual.getUsoMemoria())
                        );

                        if (!existeComponenteCPU) {
                            processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, idComponenteCPU, processoAtual.getUsoMemoria()));
                            processoModelsMySQL.add(servicosLisync.monitoramentoProcesso(processoAtual, 1, processoAtual.getUsoMemoria()));
                        }
                    }

                    ProcessoDAO processoDAO = new ProcessoDAO();
                    try {
                        processoDAO.salvarVariosProcessosSQLServer(processoModels);

                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Erro ao salvar log dos Processos no banco principal");
                    }

                    servicosLisync.registrarProcessosMySQL(processoModelsMySQL);




                    List<Janela> janelas = looca.getGrupoDeJanelas().getJanelasVisiveis();

                    List<models.Janela> janelasModelo = new ArrayList<>();
                    List<models.Janela> janelasModeloMySql = new ArrayList<>();

                    for (Janela janela : janelas) {
                        janelasModelo.add(servicosLisync.monitoramentoJanela(janela, televisao.getIdTelevisao()));
                        janelasModeloMySql.add(servicosLisync.monitoramentoJanela(janela,1));
                    }
                    servicosLisync.salvarJanelas(janelasModelo);
                    servicosLisync.salvarJanelasMySQl(janelasModeloMySql);





                    System.out.println("\n |----------- Monitoramento -----------|");
                    for (Componente componente : componentes) {
                        try {
                            String logMonitoramento = servicosLisync.monitoramentoComponentes(componente, televisao);

                            System.out.println(logMonitoramento);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    // Executar o comando
                    ComandoDAO comandoDAO = new ComandoDAO();
                    Comando comando = comandoDAO.selectComando(televisao.getIdTelevisao());

                    if (comando != null) {
                        Process processo = null;
                        String comandoExecucao = comando.getnomeComando();
                        if (comando.getResposta() == null) {
                            try {
                                System.out.println("Executando comando: " + comandoExecucao);
                                processo = Runtime.getRuntime().exec(comandoExecucao);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                                BufferedReader errorReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
                                String linha;
                                StringBuilder saidaComando = new StringBuilder();
                                StringBuilder saidaErro = new StringBuilder();

                                while ((linha = reader.readLine()) != null) {
                                    saidaComando.append(linha).append("\n");
                                }

                                while ((linha = errorReader.readLine()) != null) {
                                    saidaErro.append(linha).append("\n");
                                }

                                // Verifique se há erros na saída de erro
                                if (saidaErro.length() > 0) {
                                    System.err.println("Erro na execução do comando: " + saidaErro.toString());
                                    servicosLisync.atualizarComando(comando.getIdComando(), comando.getnomeComando(), "Erro ao executar o comando: " + saidaErro.toString(), comando.getFkTelevisao());

                                } else {
                                    System.out.println("Saída do comando: " + saidaComando.toString());
                                    servicosLisync.atualizarComando(comando.getIdComando(), comando.getnomeComando(), "Resposta: " + saidaComando.toString(), comando.getFkTelevisao());

                                }

                                // Verifique o código de saída do processo
                                int exitCode = processo.waitFor();
                                if (exitCode != 0) {
                                    System.err.println("O comando não foi executado com sucesso. Código de saída: " + exitCode);
                                    servicosLisync.atualizarComando(comando.getIdComando(), comando.getnomeComando(), "Erro ao executar o comando. Código de saída: " + exitCode, comando.getFkTelevisao());

                                }
                            } catch (IOException | InterruptedException e) {
                                servicosLisync.atualizarComando(comando.getIdComando(), comando.getnomeComando(), "Erro ao executar o comando: " + e.getMessage(), comando.getFkTelevisao());

                                System.err.println("Erro ao executar o comando ou não há comandos a serem executados no banco \n \n"+ e.getMessage());
                            } finally {
                                if (processo != null) {
                                    processo.destroy();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro crítico: " + e.getMessage());
                    e.printStackTrace();
                    timer.cancel(); // Parar o timer se ocorrer uma falha no MySQL
                }
            }
        }, inicio, intervalo);

    }
}