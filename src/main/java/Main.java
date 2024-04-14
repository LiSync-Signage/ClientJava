import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.processos.Processo;
import dao.*;
import dao.TelevisaoDAO;
import dao.UsuarioDAO;
import models.Componente;
import models.Televisao;
import models.Usuario;
import services.Autenticacao;
import services.Rede;
import services.ServicosLisync;

import java.io.IOException;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Scanner inputNext = new Scanner(System.in);
    ServicosLisync servicosLisync = new ServicosLisync();
    Looca looca = new Looca();
    Rede rede = new Rede();
    Autenticacao autenticacao = new Autenticacao();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    TelevisaoDAO televisaoDAO = new TelevisaoDAO();
    ComponenteDAO componenteDAO = new ComponenteDAO();
    ProcessoDAO processoDAO = new ProcessoDAO();

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
      if(email.equals("0")) break;

      System.out.println("Digite sua senha: ");
      String senha = input.next();
      if(senha.equals("0")) break;

      cadastroValido = autenticacao.validacaoLogin(email, senha);
      if(cadastroValido) {
        usuarioAutenticado = usuarioDAO.buscarCreedenciasUsuario(email, senha);
        System.out.println("Bem vindo %s!".formatted(usuarioAutenticado.getNome()));
          System.out.println("\nDados cliente: \n" + usuarioAutenticado.toString());
      } else {
        System.out.println("Email ou senha incorretos!");
        System.out.println("Realizar nova tentativa? (Digite 'N' Para cancelar ou 'S' para prosseguir) ");
        prosseguir = input.next();
        if(prosseguir.equalsIgnoreCase("N")) {
          System.out.println("Login encerrado");
          System.exit(0);
        }
      }
    } while (!cadastroValido);

    servicosLisync.atualizarEmpresaDoUsuario(usuarioAutenticado.getFkEmpresa());
    servicosLisync.atualizarUsuario(usuarioAutenticado);

    String endereco = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac();

    if(servicosLisync.televisaoNova(endereco, usuarioAutenticado.getFkEmpresa())) {
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

      servicosLisync.cadastrarNovaTelevisao(andar, setor, nome, taxaAtualizacao, usuarioAutenticado.getFkEmpresa());
      Televisao televisao = televisaoDAO.buscarTvPeloEndereco(endereco);
      servicosLisync.cadastrarComponentes(televisao);

      System.out.println("Televisão cadastrada com sucesso!");
    }

    Televisao televisao = televisaoDAO.buscarTvPeloEndereco(endereco);
    List<Componente> componentes = componenteDAO.buscarComponentesPorIdTv(televisao.getIdTelevisao());

    String logRegistroComponentes = "";

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

//    for (Componente componenteAtual : componentes) {
//        System.out.println(servicosLisync.monitoramentoComponentes(componenteAtual, televisao));
//    }

    // Processos

    List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();
    List<models.Processo> processoModels = new ArrayList<>();

    processos.sort(Comparator.comparing(Processo::getUsoMemoria));
    for (int i = processos.size() - 1; i > processos.size() - 6; i --) {
      Processo processoAtual = processos.get(i);
      System.out.println(""" 
               |----------- Processo %d -----------|
               Nome: %s
               Pid: %d
               CPU: %.2f
               Memória: %.2f
                """.formatted(i, processoAtual.getNome(), processoAtual.getPid(),
              processoAtual.getUsoCpu(), processoAtual.getUsoMemoria()));
      processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, televisao.getIdTelevisao()));
    }

    servicosLisync.registrarProcessos(processoModels);

    // Janelas

    List<Janela> janelas = looca.getGrupoDeJanelas().getJanelasVisiveis();
    List<models.Janela> janelasModelo = new ArrayList<>();
    for (Janela janela : janelas) {
      janelasModelo.add(servicosLisync.monitoramentoJanela(janela, televisao.getIdTelevisao()));
    }
    servicosLisync.salvarJanelas(janelasModelo);

    Timer timer = new Timer();
    int inicio = 0;
    int intervalo = televisao.getTaxaAtualizacao();

    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        System.out.println("\n |----------- Monitoramento -----------|");
        for (Componente componente : componentes) {
          try {
            String logMonitoramento = servicosLisync.monitoramentoComponentes(componente, televisao);
            System.out.println(logMonitoramento);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }, inicio, intervalo);
  }

}

