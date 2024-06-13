package conexao;

import com.github.britooo.looca.api.core.Looca;
import org.json.JSONObject;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;



    public class ConexaoSlack {

        private static HttpClient client = HttpClient.newHttpClient();
        private static final String URL = "https://hooks.slack.com/services/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";


        public void alertMessageCPU(Double cpuUsage) throws IOException, InterruptedException {
            StringBuilder alertMessage = new StringBuilder();
            if (cpuUsage > 1) {
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
               App.criarMensagem(alertMessage.toString());
            }
        }
        public void alertMessageRAM(Double memoryUsage) throws IOException, InterruptedException {
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
                App.criarMensagem(alertMessage.toString());
            }
        }
        public void  alertMessageDisco(Double diskUsage) throws IOException, InterruptedException {
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
                App.criarMensagem(alertMessage.toString());
            }
        }

        public static void sendMessage(JSONObject content) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder(URI.create(URL)).header("accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(content.toString())).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(String.format("Status: %s", response.statusCode()));
            System.out.println(String.format("Status: %s", response.body()));
        }






    }

