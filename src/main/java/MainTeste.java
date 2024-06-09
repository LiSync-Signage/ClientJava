import com.github.britooo.looca.api.core.Looca;
import dao.*;
import dao.TelevisaoDAO;
import dao.UsuarioDAO;
import models.*;
import services.Autenticacao;
import services.Monitoramento;
import services.ServicosLisync;

import java.io.IOException;
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
        Monitoramento monitoramento = new Monitoramento();

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
                System.out.println("Bem vindo %s!".formatted(usuarioAutenticado.getNomeUsuario()));
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

        String andar = null;
        String setor = null;
        String hostName = looca.getRede().getParametros().getHostName();


        if (servicosLisync.televisaoNova(hostName) && servicosLisync.contarTvsPorFkEmpresa(usuarioAutenticado.getFkEmpresa()) < servicosLisync.qtdTelevisoesDisponiveis(usuarioAutenticado.getFkEmpresa())) {
            System.out.println("Não foi possível encontrar este Tv em nossa base de dados! ");
            System.out.println("|----------- Cadastro TV -----------|");
            System.out.println("Inserir dados de localização (nome)");
            String nome = inputNext.nextLine();
            System.out.println("Definir taxa de atualização (ms)");
            Integer taxaAtualizacao = input.nextInt();

            Ambiente ambiente = new Ambiente();
            AmbienteDAO ambienteDAO = new AmbienteDAO();
            List<Ambiente> ambientes = ambienteDAO.getListaAmbientes(usuarioAutenticado.getFkEmpresa());
            if (!ambientes.isEmpty()) {
                Boolean selecaoCorreta = false;
                do {


                    Integer opcao = 1;
                    System.out.println("---------Digite a opção de ambiente desejada---------");
                    for (Ambiente ambienteAux : ambientes) {
                        System.out.println("""
                                                            
                                |------Opção %d------|
                                                            
                                Setor: %s
                                Andar: %s
                                                            
                                |--------------------|
                                """.formatted(opcao, ambienteAux.getSetor(), ambienteAux.getAndar()));
                        opcao++;

                    }
                    System.out.println("ou digite %d para criar um novo ambiente".formatted(ambientes.size() + 1));
                    Integer opcaoEscolhida = input.nextInt();
                    for (int i = 0; i < ambientes.size(); i++) {
                        if (i + 1 == opcaoEscolhida) {
                            selecaoCorreta = true;
                            ambiente = ambientes.get(i);
                            setor = ambiente.getSetor();
                            andar = ambiente.getAndar();
                        }
                    }
                    if (opcaoEscolhida == ambientes.size() + 1) {
                        System.out.println("|----------- Cadastro Ambiente -----------|");
                        System.out.println("Inserir dados de localização (andar)");
                        andar = inputNext.nextLine();
                        System.out.println("Inserir dados de localização (setor)");
                        setor = inputNext.nextLine();
                        servicosLisync.cadastrarAmbiente(setor, andar, usuarioAutenticado.getFkEmpresa());
                        selecaoCorreta = true;
                    }
                    if(!selecaoCorreta){
                        System.out.println("Selecione um ambiente válido ou digite %d para adicionar um".formatted(ambientes.size()+1));
                    }

                }while (!selecaoCorreta);


            } else {
                System.out.println("Não foi possível encontrar Nenhum ambiente em nossa base de dados! ");
                System.out.println("|----------- Cadastro Ambiente -----------|");
                System.out.println("Inserir dados de localização (andar)");
                andar = inputNext.nextLine();
                System.out.println("Inserir dados de localização (setor)");
                setor = inputNext.nextLine();
                servicosLisync.cadastrarAmbiente(setor, andar, usuarioAutenticado.getFkEmpresa());
            }


            servicosLisync.cadastrarNovaTelevisao(nome, ambienteDAO.getIdpAndarSetor(andar, setor), taxaAtualizacao);
            Televisao televisaohostname = televisaoDAO.buscarTvPeloEndereco(hostName);
            servicosLisync.cadastrarComponentes(televisaohostname);
            System.out.println("Televisão cadastrada com sucesso!");

            monitoramento.monitorarTelevisao();
        } else if (!servicosLisync.televisaoNova(hostName)) {

            monitoramento.monitorarTelevisao();
        } else {
            System.out.println("Seu plano atingiu a capacidade máxima de televisões, para cadastrar mais Televisões, entre em contato com a Lisync");
        }
    }


}

