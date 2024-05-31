import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.processos.Processo;
import dao.ComponenteDAO;
import models.Televisao;
import services.ServicosLisync;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JanelasMain {
    public static void main(String[] args){
        Looca looca = new Looca();
        List<models.Processo> processoModels = new ArrayList<>();
        ServicosLisync servicosLisync = new ServicosLisync();
        Televisao televisao = new Televisao();
        ComponenteDAO componenteDAO = new ComponenteDAO();
        List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

        List<Processo> maioresProcessosRam = new ArrayList<>();

//        for (int i = 0; i < Math.min(processos.size(), 6); i++) {
//            maioresProcessosRam.add(processos.get(i));
//        }
        for (int i = 0; i < processos.size(); i++) {
            Processo processoAtual = processos.get(i);

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

    }
}
