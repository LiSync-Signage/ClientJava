package conexao;

import org.json.JSONObject;

import java.io.IOException;

public class App {
    public static void criarMensagem(String mensagem) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();

        json.put("text", mensagem);

        ConexaoSlack slack = new ConexaoSlack();

        slack.sendMessage(json);
    }
}
