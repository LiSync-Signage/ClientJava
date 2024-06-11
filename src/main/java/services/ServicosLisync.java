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
            empresaUsuario.setIdEmpresa(1);
            empresaDAO.atualizarEmpresaLocal(empresaUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro na Atualização de usuário");
        }
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
            AmbienteDAO.insertAmbiente(ambiente);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar Ambiente");
        }
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
            televisao.setIdTelevisao(1);
            televisaoDAO.registrar(televisao);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar televisão");
        }

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
            cpu.setFkTelevisao(1);
            componenteDAO.registarComponente(cpu);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar CPU");
        }


        Disco instanciaDisco = looca.getGrupoDeDiscos().getDiscos().get(0);
        Componente disco = new Componente(instanciaDisco.getModelo(), instanciaDisco.getSerial(),
                "Disco", televisao.getIdTelevisao());
        televisao.registarComponente(disco);

        try {
            componenteDAO.registarComponenteSQLServer(disco);
            disco.setFkTelevisao(1);
            componenteDAO.registarComponente(disco);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erro ao cadastrar Disco");
        }

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
    public String monitoramentoComponentes(Componente componente, Televisao televisao) throws IOException {
        Double valor = 0.0;
        ConexaoSlack conexaoSlack = new ConexaoSlack();

        if (componente.getTipoComponente().equals("CPU")) {
            valor = looca.getProcessador().getUso();
            LogComponente logComponente = monitoramentoLogComponente(componenteDAO.buscarTipoComponentePorIdTvSQLServer("CPU", televisao.getIdTelevisao()).get(0).getIdComponente(), valor);

            logComponenteDAO.salvarLogComponenteIndividualSQLServer(logComponente);
            logComponente.setFkComponente(1);
            logComponenteDAO.salvarLogComponenteIndividual(logComponente);

            conexaoSlack.alertMessageCPU(valor);

        } else if (componente.getTipoComponente().equals("Disco")) {

            List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
            Disco discoPrincipal = discos.get(0);

            for (Disco discoAtual : discos) {
                discoPrincipal = discoAtual;
            }

            valor = (discoPrincipal.getBytesDeEscritas().doubleValue()
                    / discoPrincipal.getTamanho().doubleValue()) * 100.;
            LogComponente logComponente2 = monitoramentoLogComponente(componenteDAO.buscarTipoComponentePorIdTvSQLServer("Disco", televisao.getIdTelevisao()).get(0).getIdComponente(), valor);

            logComponenteDAO.salvarLogComponenteIndividualSQLServer(logComponente2);
            logComponente2.setFkComponente(2);
            logComponenteDAO.salvarLogComponenteIndividual(logComponente2);

            conexaoSlack.alertMessageDisco(valor);

        } else {
            valor = (looca.getMemoria().getEmUso().doubleValue() / (looca.getMemoria().getTotal().doubleValue())) * 100.;

            LogComponente logComponente1 = monitoramentoLogComponente(componenteDAO.buscarTipoComponentePorIdTvSQLServer("RAM", televisao.getIdTelevisao()).get(0).getIdComponente(), valor);

            logComponenteDAO.salvarLogComponenteIndividualSQLServer(logComponente1);
            logComponente1.setFkComponente(3);
            logComponenteDAO.salvarLogComponenteIndividual(logComponente1);

            conexaoSlack.alertMessageRAM(valor);
        }
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
        models.Janela janela = new models.Janela(janelaMonitorada.getPid().intValue(), janelaMonitorada.getComando(),
                janelaMonitorada.getTitulo(), visivel,
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
            ComandoDAO.updateComando(comandoObj);
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
