package org.LiSync.services;

import org.LiSync.models.Televisao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Rede {

    public String ipTelevisao() {
        try {
            InetAddress dados = InetAddress.getLocalHost();
            return dados.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Não foi possível retornar o IP da máquina";
        }
    }

    public Boolean statusMaquina() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
