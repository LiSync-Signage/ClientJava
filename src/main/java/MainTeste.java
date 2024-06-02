import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import dao.*;
import dao.TelevisaoDAO;
import dao.UsuarioDAO;
import models.*;
import services.Autenticacao;
import services.ServicosLisync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainTeste {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Scanner inputNext = new Scanner(System.in);
        ServicosLisync servicosLisync = new ServicosLisync();
        Looca looca = new Looca();
        Autenticacao autenticacao = new Autenticacao();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TelevisaoDAO televisaoDAO = new TelevisaoDAO();
        ComponenteDAO componenteDAO = new ComponenteDAO();

        String prosseguir;
        Boolean cadastroValido = false;
        Usuario usuarioAutenticado = null;

        do {
            System.out.println("""
                    \n|----------- LOGIN -----------|
                    Insira suas informações
                    Digite 0 para voltar ao MENU
                    """);

            System.out.println("Digite seu e-mail: ");
            String email = input.next();
            if (email.equals("0")) break;

            System.out.println("Digite sua senha: ");
            String senha = input.next();
            if (senha.equals("0")) break;

            cadastroValido = autenticacao.validacaoLogin(email, senha);
            if (cadastroValido) {
                usuarioAutenticado = usuarioDAO.buscarCreedenciasUsuario(email, senha);
                System.out.println("Bem vindo %s!".formatted(usuarioAutenticado.getNome()));
                System.out.println("\nDados cliente: \n" + usuarioAutenticado.toString());
            } else {
                System.out.println("Email ou senha incorretos!");
                System.out.println("Realizar nova tentativa? (Digite 'N' Para cancelar ou 'S' para prosseguir) ");
                prosseguir = input.next();
                if (prosseguir.equalsIgnoreCase("N")) {
                    System.out.println("Login encerrado");
                    System.exit(0);
                }
            }
        } while (!cadastroValido);

        servicosLisync.atualizarUsuario(usuarioAutenticado);
        servicosLisync.atualizarEmpresaDoUsuario(usuarioAutenticado.getFkEmpresa());


        String hostName = looca.getRede().getParametros().getHostName();


        if (servicosLisync.televisaoNova(hostName) && servicosLisync.contarTvsPorFkEmpresa(usuarioAutenticado.getFkEmpresa())
                < servicosLisync.qtdTelevisoesDisponiveis(usuarioAutenticado.getFkEmpresa())) {
            System.out.println("Não foi possível encontrar este Tv em nossa base de dados! ");
            System.out.println("|----------- Cadastro TV -----------|");
            System.out.println("Inserir dados de localização (andar)");
            String andar = inputNext.nextLine();
            System.out.println("Inserir dados de localização (setor)");
            String setor = inputNext.nextLine();
            System.out.println("Inserir dados de localização (nome)");
            String nome = inputNext.nextLine();
            System.out.println("Definir taxa de atualização (ms)");
            Integer taxaAtualizacao = input.nextInt();

            Ambiente ambiente = new Ambiente();
            AmbienteDAO ambienteDAO = new AmbienteDAO();

            servicosLisync.cadastrarAmbiente(setor, andar, usuarioAutenticado.getFkEmpresa());
            servicosLisync.cadastrarNovaTelevisao( nome, ambienteDAO.getIdpAndarSetor(andar,setor), taxaAtualizacao);
            Televisao televisaohostname = televisaoDAO.buscarTvPeloEndereco(hostName);
            servicosLisync.cadastrarComponentes(televisaohostname);
            System.out.println("Televisão cadastrada com sucesso!");

            MainTeste mainTeste = new MainTeste();
            mainTeste.monitorar();
        }else if(!servicosLisync.televisaoNova(hostName)){
            MainTeste mainTeste = new MainTeste();
            mainTeste.monitorar();



        }else{
            System.out.println("Seu plano atingiu a capacidade máxima de televisões, para cadastrar mais Televisões, entre em contato com a Lisync");
        }
    }

    public void monitorar(){


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
                    |----------- Componente %d da TV -----------|
                    Tipo do componente: %s;
                    Modelo: %s;
                    Identificador: %s;
                    Id da Televisão: %d;
                    """.formatted(componenteAtual.getIdComponente(), componenteAtual.getTipoComponente(), componenteAtual.getModelo(),
                    componenteAtual.getIdentificador(), componenteAtual.getFkTelevisao());
            System.out.println(logRegistroComponentes);
        }
        // Processos
        Timer timer = new Timer();
        int inicio = 0;
        int intervalo = televisao.getTaxaAtualizacao();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

                List<Processo> maioresProcessosRam = new ArrayList<>();

                List<models.Processo> processoModels = new ArrayList<>();

                List<models.LogComponente> logComponentesList = new ArrayList<>();

                List<Processo> maioresProcessosCPU = new ArrayList<>();

                Processador processador = new Processador();

                Memoria memoria = new Memoria();

                Collections.sort(processos, Comparator.comparingDouble(Processo::getUsoMemoria).reversed());


// 2. Iterar sobre os primeiros 4 elementos (os maiores) e adicioná-los à lista maioresProcessosRam


                for (int i = 0; i < Math.min(processos.size(), 6); i++) {
                    maioresProcessosRam.add(processos.get(i));
                }


                Collections.sort(processos, Comparator.comparingDouble(Processo::getUsoCpu).reversed());

// 2. Iterar sobre os primeiros 4 elementos (os maiores) e adicioná-los à lista maioresProcessosRam
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
                    processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, componenteDAO.buscarTipoComponentePorIdTv("RAM", televisao.getIdTelevisao()).get(0).getIdComponente(), processoAtual.getUsoMemoria()));


                }
                logComponentesList.add(servicosLisync.monitoramentoLogComponente(componenteDAO.buscarTipoComponentePorIdTv("RAM", televisao.getIdTelevisao()).get(0).getIdComponente(), (Double.valueOf(memoria.getEmUso()) * 100 / Double.valueOf(memoria.getTotal()))));

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
                            processoAtual.getUsoCpu(), processoAtual.getUsoMemoria()));
                    processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, componenteDAO.buscarTipoComponentePorIdTv("CPU", televisao.getIdTelevisao()).get(0).getIdComponente(), processoAtual.getUsoMemoria()));


                }
                logComponentesList.add(servicosLisync.monitoramentoLogComponente(componenteDAO.buscarTipoComponentePorIdTv("CPU", televisao.getIdTelevisao()).get(0).getIdComponente(), processador.getUso()));

                servicosLisync.registrarLogComponente(logComponentesList);

                servicosLisync.registrarProcessos(processoModels);

                List<Janela> janelas = looca.getGrupoDeJanelas().getJanelasVisiveis();
                List<models.Janela> janelasModelo = new ArrayList<>();


                for (Janela janela : janelas) {
                    janelasModelo.add(servicosLisync.monitoramentoJanela(janela, televisao.getIdTelevisao()));
                }
                servicosLisync.salvarJanelas(janelasModelo);
                System.out.println("\n |----------- Monitoramento -----------|");
                for (Componente componente : componentes) {
                    try {
                        String logMonitoramento = servicosLisync.monitoramentoComponentes(componente, televisao);
                        System.out.println(logMonitoramento);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                ///Executar o comando
                ComandoDAO comandoDAO = new ComandoDAO();
                Comando comando = comandoDAO.selectComando(televisao.getIdTelevisao());
                if (comando != null) {
                    Process processo = null;
                    String comandoExecucao = comando.getnomeComando();
                    if (!comandoExecucao.startsWith("Resposta:")) {
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
                                servicosLisync.atualizarComando(comando.getIdComando(), "Erro ao executar o comando: " + saidaErro.toString(), comando.getFkTelevisao());
                            } else {
                                System.out.println("Saída do comando: " + saidaComando.toString());
                                servicosLisync.atualizarComando(comando.getIdComando(), "Resposta: " + saidaComando.toString(), comando.getFkTelevisao());
                            }

                            // Verifique o código de saída do processo
                            int exitCode = processo.waitFor();
                            if (exitCode != 0) {
                                System.err.println("O comando não foi executado com sucesso. Código de saída: " + exitCode);
                                servicosLisync.atualizarComando(comando.getIdComando(), "Erro ao executar o comando. Código de saída: " + exitCode, comando.getFkTelevisao());
                            }
                        } catch (IOException | InterruptedException e) {
                            servicosLisync.atualizarComando(comando.getIdComando(), "Erro ao executar o comando: " + e.getMessage(), comando.getFkTelevisao());
                            System.err.println("Erro ao executar o comando: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            if (processo != null) {
                                processo.destroy();
                            }
                        }
                    }
                }
            }
        }, inicio, intervalo);
    }

}

