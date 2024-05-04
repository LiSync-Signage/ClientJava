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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


                    frame.dispose();

                    Looca looca = new Looca();
                    String hostName = looca.getRede().getParametros().getHostName();

                    if(!servicosLisync.televisaoNova(hostName)){

                        JFrame homePanel = new JFrame("Tela Inicial");
                        placeComponentsHome(homePanel);
                        homePanel.setSize(300, 400);
                        homePanel.setVisible(true);
                        centerFrameOnScreen(homePanel);
                        homePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    }else {
                        JFrame panelCadastroTvs = new JFrame("CadastroTvs");
                        placeComponentsCadastro(panelCadastroTvs);
                        panelCadastroTvs.setSize(300, 400);
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


    private static void placeComponentsHome(JFrame homePanel) {
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton buttonReiniciar = new JButton("Reiniciar");
        JButton buttonDesligar = new JButton("Desligar");
        JButton buttonMonitoramento = new JButton("Monitorar");

        Dimension tamanhoBotão = new Dimension(100, 50);

        buttonReiniciar.setPreferredSize(tamanhoBotão);
        buttonReiniciar.setMaximumSize(tamanhoBotão);
        buttonReiniciar.setMinimumSize(tamanhoBotão);

        buttonDesligar.setPreferredSize(tamanhoBotão);
        buttonDesligar.setMaximumSize(tamanhoBotão);
        buttonDesligar.setMinimumSize(tamanhoBotão);

        buttonMonitoramento.setPreferredSize(tamanhoBotão);
        buttonMonitoramento.setMaximumSize(tamanhoBotão);
        buttonMonitoramento.setMinimumSize(tamanhoBotão);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(buttonReiniciar);
        buttonPanel.add(Box.createRigidArea(new Dimension(90,5)));
        buttonPanel.add(buttonDesligar);
        buttonPanel.add(Box.createRigidArea(new Dimension(90,5)));
        buttonPanel.add(buttonMonitoramento);
        buttonPanel.add(Box.createVerticalGlue());

        homePanel.add(buttonPanel, BorderLayout.CENTER);
        buttonReiniciar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    String comando = "shutdown -r";
                    ServicosLisync servicosLisync = new ServicosLisync();
                    ComandoDAO comandoDAO = new ComandoDAO();
                    TelevisaoDAO televisaoDAO = new TelevisaoDAO();
                    Looca looca = new Looca();

                    String hostName = looca.getRede().getParametros().getHostName();
                    servicosLisync.cadastrarComando(comando, televisaoDAO.buscarTvPeloEndereco(hostName).getIdTelevisao());


                    Process processo = Runtime.getRuntime().exec(comando);


                    BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        System.out.println(linha);
                    }

                    // Esperando o processo terminar
                    processo.waitFor();
                } catch (IOException | InterruptedException c) {
                    c.printStackTrace();
                }
            }
        });

        buttonDesligar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    ServicosLisync servicosLisync = new ServicosLisync();

                    String comando = "shutdown";

                    TelevisaoDAO televisaoDAO = new TelevisaoDAO();
                    Looca looca = new Looca();

                    String hostName = looca.getRede().getParametros().getHostName();


                    servicosLisync.cadastrarComando(comando, televisaoDAO.buscarTvPeloEndereco(hostName).getIdTelevisao());


                    Process processo = Runtime.getRuntime().exec(comando);


                    BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        System.out.println(linha);
                    }
                    processo.waitFor();
                } catch (IOException | InterruptedException c) {
                    c.printStackTrace();
                }
            }
        });

        buttonMonitoramento.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                        JFrame panelTvs = new JFrame("Monitoramento");
                        placeComponentsPanelTvs(panelTvs);
                        panelTvs.setSize(800, 1000);
                        panelTvs.setVisible(true);
                        centerFrameOnScreen(panelTvs);
                        panelTvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        });
    }

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
                textArea.setText("");
                textArea.append("\n |----------- Monitoramento -----------|\n");


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
                    processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, componenteDAO.buscarTipoComponentePorIdTv("RAM",televisao.getIdTelevisao()).get(0).getIdComponente(), processoAtual.getUsoMemoria()));
                }
                processos.sort(Comparator.comparing(Processo::getUsoCpu));
                for (int i = processos.size() - 1; i > processos.size() - 6; i--) {
                    Processo processoAtual = processos.get(i);
                    String logProcesso = String.format(
                            "|----------- Processo %d -----------|\n" +
                                    "Nome: %s\nPid: %d\nCPU: %.2f\nMemória: %.2f\n",
                            i, processoAtual.getNome(), processoAtual.getPid(),
                            processoAtual.getUsoCpu(), processoAtual.getUsoMemoria());
                    textArea.append(logProcesso);
                    processoModels.add(servicosLisync.monitoramentoProcesso(processoAtual, componenteDAO.buscarTipoComponentePorIdTv("CPU",televisao.getIdTelevisao()).get(0).getIdComponente(), processoAtual.getUsoCpu()));
                }
                servicosLisync.registrarProcessos(processoModels);

                // Monitoramento de janelas
                List<Janela> janelas = looca.getGrupoDeJanelas().getJanelasVisiveis();
                List<models.Janela> janelasModelo = new ArrayList<>();
                for (Janela janela : janelas) {

                    String logJanela = String.format(
                            "|----------- Janelas -----------|\n" +
                                    "Título: %s\nPid: %d\nID: %d \nLocalizacao e Tamanho: %s\n",
                             janela.getTitulo(), janela.getPid(), janela.getJanelaId(), janela.getLocalizacaoETamanho());
                    textArea.append(logJanela);
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
        andarText.setBounds(80, 30, 190, 25);
        panelCadastroTvs.add(andarText);

        JLabel setorLabel = new JLabel("Setor:");
        setorLabel.setBounds(10, 60, 60, 25);
        panelCadastroTvs.add(setorLabel);

        JTextField setorText = new JTextField(20);
        setorText.setBounds(80, 60, 190, 25);
        panelCadastroTvs.add(setorText);

        JLabel nomeLabel = new JLabel("Nome personalizado:");
        nomeLabel.setBounds(10, 90, 140, 25);
        panelCadastroTvs.add(nomeLabel);

        JTextField nomeText = new JTextField(20);
        nomeText.setBounds(150, 90, 120, 25);
        panelCadastroTvs.add(nomeText);

        JLabel txAtualiLabel = new JLabel("Taxa de atualização:");
        txAtualiLabel.setBounds(10, 120, 140, 25);
        panelCadastroTvs.add(txAtualiLabel);

        JTextField txAtualiText = new JTextField(20);
        txAtualiText.setBounds(150, 120, 120, 25);
        panelCadastroTvs.add(txAtualiText);

        JButton cadTvButton = new JButton("Cadastrar Televisão");
        cadTvButton.setBounds(70, 160, 180, 25);
        panelCadastroTvs.add(cadTvButton);

        TelevisaoDAO televisaoDAO = new TelevisaoDAO();
        ServicosLisync servicosLisync = new ServicosLisync();
        Ambiente ambiente = new Ambiente();
        Looca looca = new Looca();
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        cadTvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String andar = andarText.getText();
                String setor = setorText.getText();
                String nome = nomeText.getText();
                Integer taxaAtualizacao = Integer.parseInt(txAtualiText.getText());



                String hostName = looca.getRede().getParametros().getHostName();


                servicosLisync.cadastrarAmbiente(setor, andar, usuarioAutenticado.getFkEmpresa());
                servicosLisync.cadastrarNovaTelevisao( nome, ambienteDAO.getIdpAndarSetor(andar,setor), taxaAtualizacao);
                System.out.println(andar);
                Televisao televisao = televisaoDAO.buscarTvPeloEndereco(hostName);
                servicosLisync.cadastrarComponentes(televisao);


                JFrame panelTvs = new JFrame("Janela Secundária");
                placeComponentsPanelTvs(panelTvs);
                panelTvs.setSize(800, 1000);
                panelTvs.setVisible(true);
                centerFrameOnScreen(panelTvs);
                panelTvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                panelCadastroTvs.dispose();


            }
        });
    }



}