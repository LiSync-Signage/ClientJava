import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import models.Televisao;
import services.ServicosLisync;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JanelasMain {
    public static void main(String[] args){
        Looca looca = new Looca();
        List<Janela> janelas = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        ServicosLisync servicosLisync = new ServicosLisync();
        Televisao televisao = new Televisao();

        Integer input = scanner.nextInt();

        for (Janela janela : looca.getGrupoDeJanelas().getJanelasVisiveis()){
            if (janela.getLocalizacaoETamanho().width >= input){
                janelas.add(janela);
            }
        }

        List<models.Janela> janelasModelo = new ArrayList<>();


        for (Janela janela : janelas) {
            janelasModelo.add(servicosLisync.monitoramentoJanela(janela, televisao.getIdTelevisao()));
        }
        servicosLisync.salvarJanelas(janelasModelo);

        System.out.println(janelas);

    }
}
