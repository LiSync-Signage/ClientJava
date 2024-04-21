import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.*;
import services.Autenticacao;
import dao.UsuarioDAO;


import services.ServicosLisync;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.processos.Processo;
import dao.*;
import dao.TelevisaoDAO;
import models.Usuario;


import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class Main {
    private static JFrame frame;
    private static Usuario usuarioAutenticado;




    public static void main(String[] args) {
        frame = new JFrame("Login");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelLogin = new JPanel(new GridBagLayout());
        frame.add(panelLogin);
        placeComponents(panelLogin);
        frame.setVisible(true);
        frame.setResizable(false);
        centerFrameOnScreen(frame);

    }


    //Janela Login
    private static void placeComponents(JPanel panelLogin) {


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Email:");
        panelLogin.add(emailLabel, constraints);

        JTextField emailText = new JTextField(20);
        constraints.gridy = 1;
        panelLogin.add(emailText, constraints);

        JLabel senhaLabel = new JLabel("Senha:");
        constraints.gridy = 2;
        panelLogin.add(senhaLabel, constraints);

        JPasswordField senhaText = new JPasswordField(20);
        constraints.gridy = 3;
        panelLogin.add(senhaText, constraints);

        JButton loginButton = new JButton("Login");
        constraints.gridy = 4;
        panelLogin.add(loginButton, constraints);


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailText.getText();
                String senha = new String(senhaText.getPassword());
                Autenticacao autenticacao = new Autenticacao();
                ServicosLisync servicosLisync = new ServicosLisync();
                boolean autenticado = autenticacao.validacaoLogin(email, senha);


                if (autenticado) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    usuarioAutenticado = usuarioDAO.buscarCreedenciasUsuario(email, senha);
                    JOptionPane.showMessageDialog(null, "Bem-vindo, " + usuarioAutenticado.getNome() + "!");
                    servicosLisync.atualizarEmpresaDoUsuario(usuarioAutenticado.getFkEmpresa());
                    servicosLisync.atualizarUsuario(usuarioAutenticado);
                    frame.dispose(); // Fechar a janela JFrame
                    Looca looca = new Looca();
                    String hostName = looca.getRede().getParametros().getHostName();
                    usuarioAutenticado = usuarioDAO.buscarCreedenciasUsuario(email, senha);


                    ///Verificação de existência de televisões na empresa
                    if(!servicosLisync.televisaoNova(hostName, usuarioAutenticado.getFkEmpresa())){
                        JFrame panelTvs = new JFrame("Janela Secundária");
                        placeComponentsPanelTvs(panelTvs);
                        panelTvs.setSize(400, 700);
                        panelTvs.setVisible(true);
                        centerFrameOnScreen(panelTvs);
                        panelTvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



                    }else {
                        JFrame panelCadastroTvs = new JFrame("CadastroTvs");
                        placeComponentsCadastro(panelCadastroTvs);
                        panelCadastroTvs.setSize(300, 200);
                        panelCadastroTvs.setVisible(true);
                        centerFrameOnScreen(panelCadastroTvs);
                        panelCadastroTvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    }



                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha incorretos!");
                }
            }
        });


    }

    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(centerX, centerY);
    }



    ///Janela de Tvs
    private static void placeComponentsPanelTvs(JFrame panelTvs) {
        panelTvs.setLayout(new BorderLayout());
        Looca looca = new Looca();
        String hostName = looca.getRede().getParametros().getHostName();
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        panelTvs.add(new JScrollPane(textArea), BorderLayout.CENTER);
        ServicosLisync servicosLisync = new ServicosLisync();

        TelevisaoDAO televisaoDAO = new TelevisaoDAO();
        Televisao televisao = televisaoDAO.buscarTvPeloEndereco(hostName);


        ComponenteDAO componenteDAO = new ComponenteDAO();

        List<Componente> componentes = componenteDAO.buscarComponentesPorIdTv(televisao.getIdTelevisao());

        Timer timer = new Timer();
        int intervalo = televisao.getTaxaAtualizacao();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                textArea.setText(""); // Limpar o JTextArea a cada atualização
                textArea.append("\n |----------- Monitoramento -----------|\n");

                // Monitoramento de processos


                List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();
                processos.sort(Comparator.comparing(Processo::getUsoMemoria));
                List<models.Processo> processoModels = new ArrayList<>();
                for (int i = processos.size() - 1; i > processos.size() - 6; i--) {
                    Processo processoAtual = processos.get(i);
                    String logProcesso = String.format(
                            "|----------- Processo %d -----------|\n" +
                                    "Nome: %s\nPid: %d\nCPU: %.2f\nMemória: %.2f\n",
                            i, processoAtual.getNome(), processoAtual.getPid(),
                            processoAtual.getUsoCpu(), processoAtual.getUsoMemoria());
                    textArea.append(logProcesso);
                    processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, televisao.getIdTelevisao()));
                }
                servicosLisync.registrarProcessos(processoModels);

                // Monitoramento de janelas
                List<Janela> janelas = looca.getGrupoDeJanelas().getJanelasVisiveis();
                List<models.Janela> janelasModelo = new ArrayList<>();
                for (Janela janela : janelas) {
                    janelasModelo.add(servicosLisync.monitoramentoJanela(janela, televisao.getIdTelevisao()));
                }
                servicosLisync.salvarJanelas(janelasModelo);

                // Monitoramento de componentes
                for (Componente componente : componentes) {
                    try {
                        String logMonitoramento = servicosLisync.monitoramentoComponentes(componente, televisao);
                        textArea.append(logMonitoramento + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 0, intervalo);
    }


    ///Janela de Cadastro de Tvs
    private static void placeComponentsCadastro(JFrame panelCadastroTvs) {
        panelCadastroTvs.setLayout(null);

        JLabel andarLabel = new JLabel("Andar:");
        andarLabel.setBounds(10, 30, 60, 25);
        panelCadastroTvs.add(andarLabel);

        JTextField andarText = new JTextField(20);
        andarText.setBounds(80, 30, 190, 25); // Centralizado horizontalmente
        panelCadastroTvs.add(andarText);

        JLabel setorLabel = new JLabel("Setor:");
        setorLabel.setBounds(10, 60, 60, 25);
        panelCadastroTvs.add(setorLabel);

        JTextField setorText = new JTextField(20);
        setorText.setBounds(80, 60, 190, 25); // Centralizado horizontalmente
        panelCadastroTvs.add(setorText);

        JLabel nomeLabel = new JLabel("Nome personalizado:");
        nomeLabel.setBounds(10, 90, 140, 25); // Alinhado à esquerda
        panelCadastroTvs.add(nomeLabel);

        JTextField nomeText = new JTextField(20);
        nomeText.setBounds(150, 90, 120, 25); // Centralizado horizontalmente
        panelCadastroTvs.add(nomeText);

        JLabel txAtualiLabel = new JLabel("Taxa de atualização:");
        txAtualiLabel.setBounds(10, 120, 140, 25); // Alinhado à esquerda
        panelCadastroTvs.add(txAtualiLabel);

        JTextField txAtualiText = new JTextField(20);
        txAtualiText.setBounds(150, 120, 120, 25); // Centralizado horizontalmente
        panelCadastroTvs.add(txAtualiText);

        JButton cadTvButton = new JButton("Cadastrar Televisão");
        cadTvButton.setBounds(70, 160, 180, 25); // Centralizado horizontalmente
        panelCadastroTvs.add(cadTvButton);
        cadTvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String andar = andarText.getText();
                String setor = setorText.getText();
                String nome = nomeText.getText();
                Integer taxaAtualizacao = Integer.parseInt(txAtualiText.getText());

                TelevisaoDAO televisaoDAO = new TelevisaoDAO();
                ServicosLisync servicosLisync = new ServicosLisync();
                Looca looca = new Looca();

                String hostName = looca.getRede().getParametros().getHostName();



                servicosLisync.cadastrarNovaTelevisao(andar, setor, nome, taxaAtualizacao, usuarioAutenticado.getFkEmpresa());
                Televisao televisao = televisaoDAO.buscarTvPeloEndereco(hostName);
                servicosLisync.cadastrarComponentes(televisao);


                JFrame panelTvs = new JFrame("Janela Secundária");
                placeComponentsPanelTvs(panelTvs);
                panelTvs.setSize(300, 200);
                panelTvs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


                panelTvs.setVisible(true);
                frame.dispose();


            }
        });
    }



}