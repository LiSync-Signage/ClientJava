package org.LiSync.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.util.Conversor;
import org.LiSync.dao.ComponenteDAO;
import org.LiSync.dao.EmpresaDAO;
import org.LiSync.dao.TelevisaoDAO;
import org.LiSync.dao.UsuarioDAO;
import org.LiSync.models.Componente;
import org.LiSync.models.Empresa;
import org.LiSync.models.Televisao;
import org.LiSync.models.Usuario;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;

import java.util.ArrayList;
import java.util.List;

public class ServicosLisync {
    Looca looca = new Looca();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    EmpresaDAO empresaDAO = new EmpresaDAO();
    TelevisaoDAO televisaoDAO = new TelevisaoDAO();
    ComponenteDAO componenteDAO = new ComponenteDAO();


    public Boolean televisaoNova(String ipTv) {
        Integer televisaoExiste = televisaoDAO.contarIpTv(ipTv);
        return televisaoExiste == 0;
    }

    public void atualizarEmpresaDoUsuario(Integer fkEmpresa) {
        Empresa empresaUsuario = empresaDAO.buscarEmpresa(fkEmpresa);
        empresaDAO.atualizarEmpresaLocal(empresaUsuario);
    }

    public void atualizarUsuario(Usuario usuarioLogado) {
        usuarioDAO.atualizarUsuarioLocal(usuarioLogado);
    }

    public void cadastrarNovaTelevisao(String andar, String setor, Integer taxaAtualizacao,
                                        String ipTelevisao, Boolean statusMaquina,Integer idEmpresa) {
        Televisao televisao = new Televisao(
                andar,
                setor,
                taxaAtualizacao,
                ipTelevisao,
                statusMaquina,
                looca.getSistema().getSistemaOperacional(),
                idEmpresa
        );

        televisaoDAO.registrar(televisao);
        System.out.println("Nova televisão adicionada! \n");
    }

    public void cadastrarComponentes(Televisao televisao) {
        televisao.setComponentes(new ArrayList<>());
        Componente cpu = new Componente(looca.getProcessador().getNome(), televisao.getIdTelevisao(), 1);
        televisao.adicionarComponente(cpu);
        componenteDAO.registarComponente(cpu);

        DiscoGrupo discoGrupo = looca.getGrupoDeDiscos();
        List<Disco> discos = discoGrupo.getDiscos();
        for (Disco discoAtual : discos) {
            Componente disco = new Componente(discoAtual.getModelo(), televisao.getIdTelevisao(), 2);
            televisao.adicionarComponente(disco);
            componenteDAO.registarComponente(disco);
        }

        Componente memoriaRam = new Componente(String.format("Memória RAM %s",
                Conversor.formatarBytes(looca.getMemoria().getTotal())) , televisao.getIdTelevisao(), 3);
        televisao.adicionarComponente(memoriaRam);
        componenteDAO.registarComponente(memoriaRam);

        SystemInfo si = new SystemInfo();
        List<GraphicsCard> placasDeVideo = si.getHardware().getGraphicsCards();
        for (GraphicsCard gpuDaVez : placasDeVideo) {
            Componente gpu = new Componente(gpuDaVez.getName(), televisao.getIdTelevisao(), 4);
            televisao.adicionarComponente(gpu);
            componenteDAO.registarComponente(gpu);
        }
    }

//    public String monitoramentoComponentes(Componente componente, Televisao televisao) {
//        Double valor = 0.0;
//        switch (componente.getFkTipoComponente()) {
//            case 1:
//                valor = looca.getProcessador().getUso();
//                if (valor > 80) {
//                    return String.format("ESTADO CRÍTICO - Uso da CPU elevado na televisão de ip "
//                            + televisao.getIpTv() + " Uso de CPU: " + valor);
//                } else if (valor > 60) {
//                    return String.format("ESTADO ATENÇÃO - Uso da CPU moderado na televisão de ip "
//                            + televisao.getIpTv() + " Uso de CPU: " + valor);
//                } else {
//                    return String.format("ESTADO OK - Uso da CPU conforme as regras de aceite"
//                            + televisao.getIpTv() + " Uso de CPU: " + valor);
//                }
//            case 2:
//                List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
//                Disco discoPrincipal = discos.get(0);
//                for (Disco discoAtual : discos) {
//                    discoPrincipal = discoAtual;
//                }
//                valor = (discoPrincipal.getBytesDeEscritas().doubleValue()
//                        / discoPrincipal.getTamanho().doubleValue()) * 100.0;
//                return String.format("Uso de disco: " + valor);
//            case 3:
//                valor = looca.getMemoria().getEmUso().doubleValue();
//                return String.format("Uso de memória RAM: " + valor);
//            default:
//                System.out.println("Valor inválido");
//        }
//        return "OK";
//    }

}
