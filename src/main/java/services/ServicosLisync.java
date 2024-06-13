package services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.util.Conversor;
import conexao.ConexaoSlack;
import dao.*;
import models.*;
import plano.Plano;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicosLisync {
    Looca looca = new Looca();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    EmpresaDAO empresaDAO = new EmpresaDAO();
    TelevisaoDAO televisaoDAO = new TelevisaoDAO();
    ComponenteDAO componenteDAO = new ComponenteDAO();
    ProcessoDAO processoDAO = new ProcessoDAO();
    JanelaDAO janelaDAO = new JanelaDAO();

    LogComponenteDAO logComponenteDAO = new LogComponenteDAO();

    //
    public Boolean televisaoNova(String hostname) {
        Integer televisaoExiste = televisaoDAO.contarPorEndereco(hostname);
        return televisaoExiste == 0;
    }

    //
    public Integer qtdTelevisoesDisponiveis(Integer fkEmpresa) {
        Plano plano = empresaDAO.buscarPorPlano(fkEmpresa).getPlano();
        return plano.getQtdTvs();
    }

    //
    public Integer contarTvsPorFkEmpresa(Integer fkEmpresa) {
        Integer qtdTvs = empresaDAO.contarPorEmpresa(fkEmpresa);
        return qtdTvs;
    }

    //
    public void atualizarEmpresaDoUsuario(Integer fkEmpresa) {
        Empresa empresaUsuario = empresaDAO.buscarEmpresa(fkEmpresa);
        try {
            empresaDAO.atualizarEmpresaLocalSQLServer(empresaUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro na Atualização de usuário");
        }
        empresaUsuario.setIdEmpresa(1);
        empresaDAO.atualizarEmpresaLocal(empresaUsuario);
    }

    //
    public void atualizarUsuario(Usuario usuarioLogado) {
        try {
            usuarioDAO.atualizarUsuarioLocalSQLServer(usuarioLogado);
            Empresa EmpresaUser = empresaDAO.buscarEmpresa(usuarioLogado.getFkEmpresa());
            empresaDAO.atualizarEmpresaLocal(EmpresaUser);
            usuarioLogado.setFkEmpresa(EmpresaUser.getIdEmpresa());
            usuarioDAO.atualizarUsuarioLocal(usuarioLogado);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro na Atualização de usuário");
        }


    }

    //
    public void cadastrarAmbiente(String setor, String andar, Integer fkEmpresa) {
        models.Ambiente ambiente = new models.Ambiente(setor, andar, fkEmpresa);
        registrarAmbiente(ambiente);
    }

    public void registrarAmbiente(Ambiente ambiente) {
        try {
            AmbienteDAO.insertAmbienteSQLServer(ambiente);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar Ambiente");
        }
        AmbienteDAO.insertAmbiente(ambiente);
    }


    //
    public void cadastrarNovaTelevisao(String nome, Integer fkAmbiente, Integer taxaAtualizacao
    ) {

        Televisao televisao = new Televisao(
                nome,
                fkAmbiente,
                taxaAtualizacao,
                looca.getRede().getParametros().getHostName()
        );

        System.out.println(televisao.getFkAmbiente());

        try {
            televisaoDAO.registrarSQLServer(televisao);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar televisão");
        }
        televisao.setIdTelevisao(1);
        televisaoDAO.registrar(televisao);

        System.out.println("Nova televisão adicionada! \n");
    }

    //
    public void cadastrarComponentes(Televisao televisao) {
        televisao.setComponentes(new ArrayList<>());

        Componente cpu = new Componente(looca.getProcessador().getNome(),
                looca.getProcessador().getId(), "CPU", televisao.getIdTelevisao());
        televisao.registarComponente(cpu);

        try {
            componenteDAO.registarComponenteSQLServer(cpu);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar CPU");
        }
        cpu.setFkTelevisao(1);
        componenteDAO.registarComponente(cpu);


        Disco instanciaDisco = looca.getGrupoDeDiscos().getDiscos().get(0);
        Componente disco = new Componente(instanciaDisco.getModelo(), instanciaDisco.getSerial(),
                "Disco", televisao.getIdTelevisao());
        televisao.registarComponente(disco);

        try {
            componenteDAO.registarComponenteSQLServer(disco);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar Disco");
        }
        disco.setFkTelevisao(1);
        componenteDAO.registarComponente(disco);

        Long memoriaTotal = looca.getMemoria().getTotal();
        Componente memoriaRam = new Componente(String.format("Memória RAM %s", Conversor.formatarBytes(memoriaTotal)),
                "Não existe", "RAM", televisao.getIdTelevisao());
        televisao.registarComponente(memoriaRam);

        try {
            componenteDAO.registarComponenteSQLServer(memoriaRam);
            memoriaRam.setFkTelevisao(1);
            componenteDAO.registarComponente(memoriaRam);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar Memoria ram");
        }


    }

    //
    public String monitoramentoComponentes(Componente componente, Televisao televisao) throws IOException, InterruptedException {
        Double valor = 0.0;
        ConexaoSlack conexaoSlack = new ConexaoSlack();
        List<LogComponente> listaLogSQLServer = new ArrayList<>();
        List<LogComponente> listaLogMySQL = new ArrayList<>();

        if (componente.getTipoComponente().equals("CPU")) {
            valor = looca.getProcessador().getUso();
            LogComponente logComponente = monitoramentoLogComponente(componente.getIdComponente(), valor);

            try{
                listaLogSQLServer.add(logComponente);
                LogComponente logComponenteMysql1 = new LogComponente(logComponente) ;
                logComponenteMysql1.setFkComponente(1);
                listaLogMySQL.add(logComponenteMysql1);
            }catch (Exception e){
                e.printStackTrace();
            }


            conexaoSlack.alertMessageCPU(valor);

        } else if (componente.getTipoComponente().equals("Disco")) {

            List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
            Disco discoPrincipal = discos.get(0);

            for (Disco discoAtual : discos) {
                discoPrincipal = discoAtual;
            }

            valor = (discoPrincipal.getBytesDeEscritas().doubleValue()
                    / discoPrincipal.getTamanho().doubleValue()) * 100.;
            LogComponente logComponente2 = monitoramentoLogComponente(componente.getIdComponente(), valor);
            try{
                listaLogSQLServer.add(logComponente2);
                LogComponente logComponenteMysql2 = new LogComponente(logComponente2) ;
                logComponenteMysql2.setFkComponente(2);
                listaLogMySQL.add(logComponenteMysql2);
            }catch (Exception e){
                e.printStackTrace();
            }


            conexaoSlack.alertMessageDisco(valor);

        } else {
            valor = (looca.getMemoria().getEmUso().doubleValue() / (looca.getMemoria().getTotal().doubleValue())) * 100.;

            LogComponente logComponente1 = monitoramentoLogComponente(componente.getIdComponente(), valor);

            try{
                listaLogSQLServer.add(logComponente1);
                LogComponente logComponenteMysql3 = new LogComponente(logComponente1);
                logComponenteMysql3.setFkComponente(3);
                listaLogMySQL.add(logComponenteMysql3);
            }catch (Exception e){
                e.printStackTrace();
            }

            conexaoSlack.alertMessageRAM(valor);
        }
        logComponenteDAO.salvarLogComponenteSQLServer(listaLogSQLServer);
        logComponenteDAO.salvarLogComponente(listaLogMySQL);
        return "";
    }

    //
    public void registrarProcessos(List<models.Processo> listaProcessos) {
        processoDAO.salvarVariosProcessosSQLServer(listaProcessos);
    }

    public void registrarProcessosMySQL(List<models.Processo> listaProcessos) {
        processoDAO.salvarVariosProcessos(listaProcessos);
    }

    public void registrarLogComponente(List<models.LogComponente> listaLogComponente) {
        logComponenteDAO.salvarLogComponenteSQLServer(listaLogComponente);
    }

    public void registrarLogComponenteMySQL(List<models.LogComponente> listaLogComponente) {
        logComponenteDAO.salvarLogComponente(listaLogComponente);
    }

//

    public models.LogComponente monitoramentoLogComponente(Integer fkComponente, Double valor) {
        models.LogComponente logComponente = new models.LogComponente(fkComponente, valor);
        return logComponente;
    }

    //
    public models.Processo monitoramentoProcesso(Processo processoMonitorado, Integer idComponente, Double valor) {
        models.Processo processo = new models.Processo(processoMonitorado.getPid(),
                processoMonitorado.getNome(), idComponente, valor);
        return processo;
    }

    //
    public models.Janela monitoramentoJanela(Janela janelaMonitorada, Integer idTelevisao) {
        Integer visivel = (janelaMonitorada.isVisivel() ? 1 : 0);
        models.Janela janela = new models.Janela(janelaMonitorada.getPid().intValue(), janelaMonitorada.getTitulo(),
                janelaMonitorada.getComando(), visivel,
                idTelevisao);
        return janela;
    }

    //
    public void salvarJanelas(List<models.Janela> janelas) {


        janelaDAO.salvarVariasJanelasSQLServer(janelas);


    }

    public void salvarJanelasMySQl(List<models.Janela> janelas) {


        janelaDAO.salvarVariasJanelas(janelas);


    }


    public void atualizarComando(Integer idComando, String comando, String resposta, Integer fkTelevisao) {
        models.Comando comandoObj = new models.Comando(idComando, comando, resposta, fkTelevisao);

        try {
            ComandoDAO.updateComandoSQLServer(comandoObj);
            comandoObj.setFkTelevisao(1);
            ComandoDAO.insertComando(comandoObj);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao retornar resposta do comando");
        }
    }

    public void inserirComandos(Comando comando) {
        ComandoDAO comandoDAO = new ComandoDAO();

        try {
            comandoDAO.insertComandoSQLServer(comando);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                comandoDAO.insertComando(comando);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inserirComandoLocal(Comando comando) {
        ComandoDAO comandoDAO = new ComandoDAO();

        try {
            comandoDAO.insertComando(comando);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
