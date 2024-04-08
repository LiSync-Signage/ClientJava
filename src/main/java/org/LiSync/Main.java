package org.LiSync;

import com.github.britooo.looca.api.core.Looca;
import org.LiSync.dao.ComponenteDAO;
import org.LiSync.dao.TelevisaoDAO;
import org.LiSync.dao.UsuarioDAO;
import org.LiSync.models.Componente;
import org.LiSync.models.Televisao;
import org.LiSync.models.Usuario;
import org.LiSync.services.Autenticacao;
import org.LiSync.services.Rede;
import org.LiSync.services.ServicosLisync;

import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    ServicosLisync servicosLisync = new ServicosLisync();
    Looca looca = new Looca();
    Rede rede = new Rede();
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

    String ipMaquina = rede.ipTelevisao();
    Boolean status = rede.statusMaquina();

    if(servicosLisync.televisaoNova(ipMaquina)) {
      System.out.println("Não foi possível encontrar este Tv em nossa base de dados! ");
      System.out.println("|----------- Cadastro TV -----------|");
      System.out.println("Inserir dados de localização (andar)");
      String andar = input.next();
      System.out.println("Inserir dados de localização (setor)");
      String setor = input.next();
      System.out.println("Definir taxa de atualização (ms)");
      Integer taxaAtualizacao = input.nextInt();

      servicosLisync.cadastrarNovaTelevisao(andar, setor, taxaAtualizacao,
              ipMaquina, status, usuarioAutenticado.getFkEmpresa());
      Televisao televisao = televisaoDAO.buscarTvPeloIpTv(ipMaquina);
      servicosLisync.cadastrarComponentes(televisao);

      System.out.println("Televisão cadastrada com sucesso!");
    }

    Televisao televisao = televisaoDAO.buscarTvPeloIpTv(ipMaquina);
    List<Componente> componentes = componenteDAO.buscarComponentesPorIdTv(televisao.getIdTelevisao());

    System.out.println(componentes);

//    for (Componente componenteAtual : componentes) {
//      servicosLisync.monitoramentoComponentes(componenteAtual, televisao);
//    }

  }

}