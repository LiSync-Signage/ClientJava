package services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.util.Conversor;
import dao.*;
import models.*;


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


    public Boolean televisaoNova(String endereco, Integer idEmpresa) {
        Integer televisaoExiste = televisaoDAO.contarPorEndereco(endereco, idEmpresa);
        return televisaoExiste == 0;
    }

    public void atualizarEmpresaDoUsuario(Integer fkEmpresa) {
        Empresa empresaUsuario = empresaDAO.buscarEmpresa(fkEmpresa);
        empresaDAO.atualizarEmpresaLocal(empresaUsuario);
    }

    public void atualizarUsuario(Usuario usuarioLogado) {
        usuarioDAO.atualizarUsuarioLocal(usuarioLogado);
    }

    public void cadastrarNovaTelevisao(String andar, String setor, String nome, Integer taxaAtualizacao,
                                       Integer idEmpresa) {
        Televisao televisao = new Televisao(
                andar,
                setor,
                nome,
                taxaAtualizacao,
                gerarSerial(looca.getRede().getGrupoDeInterfaces().getInterfaces()),
                idEmpresa
        );

        televisaoDAO.registrar(televisao);
        System.out.println("Nova televisão adicionada! \n");
    }

    public String gerarSerial(List<RedeInterface> redeInterfaces) {
        String serial = "";
        for (RedeInterface redeInterface : redeInterfaces) {
            serial = String.format("%s%s", serial, redeInterface.getEnderecoMac());
        }
        return serial;
    }

    public void cadastrarComponentes(Televisao televisao) {
        televisao.setComponentes(new ArrayList<>());
        Componente cpu = new Componente(looca.getProcessador().getNome(),
                looca.getProcessador().getId(), "CPU", televisao.getIdTelevisao());
        televisao.registarComponenteTv(cpu);
        componenteDAO.registarComponente(cpu);

        Disco instanciaDisco = looca.getGrupoDeDiscos().getDiscos().get(0);
        Componente disco = new Componente(instanciaDisco.getModelo(), instanciaDisco.getSerial(),
                "Disco", televisao.getIdTelevisao());
        televisao.registarComponenteTv(disco);
        componenteDAO.registarComponente(disco);

        Long memoriaTotal = looca.getMemoria().getTotal();
        Componente memoriaRam = new Componente(String.format("Memória RAM %s", Conversor.formatarBytes(memoriaTotal)),
                "Não existe", "RAM", televisao.getIdTelevisao());
        televisao.registarComponenteTv(memoriaRam);
        componenteDAO.registarComponente(memoriaRam);

    }

    public String monitoramentoComponentes(Componente componente, Televisao televisao) throws IOException {
        Double valor = 0.0;
        switch (componente.getTipoComponente()) {
            case "CPU":

                valor = looca.getProcessador().getUso();

                if (valor > 80) {
                    return String.format("ESTADO CRÍTICO - Uso da CPU elevado na televisão "
                            + televisao.getNome() + " Uso de CPU: " + valor);
                } else if (valor > 60) {
                    return String.format("ESTADO ATENÇÃO - Uso da CPU moderado na televisão "
                            + televisao.getNome() + " Uso de CPU: " + valor);
                } else {
                    return String.format("ESTADO OK - Uso da CPU conforme as regras de aceite | Televisão: "
                            + televisao.getNome() + " | Uso de CPU: " + valor);
                }

            case "Disco":
                List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
                Disco discoPrincipal = discos.get(0);

                for (Disco discoAtual : discos) {
                    discoPrincipal = discoAtual;
                }

                valor = (discoPrincipal.getBytesDeEscritas().doubleValue()
                        / discoPrincipal.getTamanho().doubleValue()) * 100.;

                if (valor > 60) {
                    return String.format("ESTADO CRÍTICO - Uso da Disco elevado na televisão "
                            + televisao.getNome() + " Uso de Disco: " + valor);
                } else if (valor > 30) {
                    return String.format("ESTADO ATENÇÃO - Uso da Disco moderado na televisão "
                            + televisao.getNome() + " Uso de Disco: " + valor);
                } else {
                    return String.format("ESTADO OK - Uso da Disco conforme as regras de aceite | Televisão: "
                            + televisao.getNome() + " | Uso de Disco: " + valor);
                }

            case "RAM":
                valor = (looca.getMemoria().getEmUso().doubleValue() / (looca.getMemoria().getTotal().doubleValue())) * 100.;

                if (valor > 90) {
                    return String.format("ESTADO CRÍTICO - Uso da RAM elevado na televisão "
                            + televisao.getNome() + " Uso de RAM: " + valor);
                } else if (valor > 75) {
                    return String.format("ESTADO ATENÇÃO - Uso da RAM moderado na televisão "
                            + televisao.getNome() + " Uso de RAM: " + valor);
                } else {
                    return String.format("ESTADO OK - Uso da RAM conforme as regras de aceite | Televisão: "
                            + televisao.getNome() + " | Uso de RAM: " + valor);
                }

            default:
                System.out.println("Valor inválido");
        }
        return "Não foi possível encontar componentes";
    }

    public void registrarProcessos(List<models.Processo> listaProcessos) {
        processoDAO.salvarVariosProcessos(listaProcessos);
    }

    public models.Processo monitoramentoProcesso(Processo processoMonitorado, Integer idTelevisao) {
        models.Processo processo = new models.Processo(processoMonitorado.getPid(),
                processoMonitorado.getNome(), idTelevisao);
        return processo;
    }

    public models.Janela monitoramentoJanela(Janela janelaMonitorada, Integer idTelevisao) {
        Integer visivel = (janelaMonitorada.isVisivel() ? 1 : 0);
        String tamanhoJanela = "Altura: %dpx | Largura: %dpx".formatted(janelaMonitorada.getLocalizacaoETamanho().height,
                janelaMonitorada.getLocalizacaoETamanho().width);
        models.Janela janela = new models.Janela(janelaMonitorada.getPid().intValue(), janelaMonitorada.getComando(),
                janelaMonitorada.getTitulo(), tamanhoJanela, visivel,
                idTelevisao);
        return  janela;
    }

    public void salvarJanelas(List<models.Janela> janelas) {
        janelaDAO.salvarVariasJanelas(janelas);
    }
}
