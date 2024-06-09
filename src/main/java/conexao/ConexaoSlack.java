package conexao;

import com.github.britooo.looca.api.core.Looca;

import javax.sound.midi.Soundbank;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


    public class ConexaoSlack {

        private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";
        private static final String AUTH_TOKEN = "xoxb-7190971444085-7179574682359-vP4KAoQfRLv7fVx4Sl5kbeUX";
        private static final String CHANNEL = "C075LV4CHRB";

        public void alertMessageCPU(Double cpuUsage) {
            StringBuilder alertMessage = new StringBuilder();
            if (cpuUsage > 80) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("CPU Crítico: Uso acima de 80% (").append(cpuUsage).append("%)\n");
                System.out.println("aleta de uso crítico de CPU (acima de 80%)");
            } else if (cpuUsage > 60) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("CPU Alerta: Uso entre 61% e 80% (").append(cpuUsage).append("%)\n");
                System.out.println("aleta de uso de CPU (entre 61% e 80%)");
            }else{
                System.out.println("uso de CPU dentro dos conformes");
            }
            if (!alertMessage.isEmpty()) {
                sendSlackMessage(CHANNEL, alertMessage.toString());
            }
        }
        public void alertMessageRAM(Double memoryUsage) {
            StringBuilder alertMessage = new StringBuilder();
            if (memoryUsage > 90) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("Memória Crítico: Uso acima de 90% (").append(memoryUsage).append("%)\n");
                System.out.println("aleta de uso crítico de memória ram (acima de 90%)");
            } else if (memoryUsage > 75) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("Memória Alerta: Uso entre 75% e 90% (").append(memoryUsage).append("%)\n");
                System.out.println("aleta de uso de memória RAM (entre 75% e 90%)");
            }else{
                System.out.println("uso de memória RAM dentro dos conformes");
            }

            if (!alertMessage.isEmpty()) {
                sendSlackMessage(CHANNEL, alertMessage.toString());
            }
        }
        public void  alertMessageDisco(Double diskUsage) {
            StringBuilder alertMessage = new StringBuilder();

            if (diskUsage > 60) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("Disco Crítico: Uso acima de 60% (").append(diskUsage).append("%)\n");
                System.out.println("aleta de uso crítico do disco (acima de 60%)");
            } else if (diskUsage > 30) {
                alertMessage.append("Alertas de uso de recursos:\n");
                alertMessage.append("Disco Alerta: Uso entre 31% e 60% (").append(diskUsage).append("%)\n");
                System.out.println("aleta de uso do disco (entre 31% e 60%)");
            }else{
                System.out.println("uso de Disco dentro dos conformes");
            }
            if (!alertMessage.isEmpty()) {
                sendSlackMessage(CHANNEL, alertMessage.toString());
            }
        }

        public static void sendSlackMessage(String channel, String text) {
            try {
                URL url = new URL(SLACK_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);

                String postData = "channel=" + channel + "&text=" + text;
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postDataBytes);
                    os.flush();
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Mensagem enviada com sucesso ao Slack.");
                } else {
                    System.out.println("Erro ao enviar mensagem ao Slack. Código de resposta: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

