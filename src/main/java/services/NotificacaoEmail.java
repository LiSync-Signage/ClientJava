package services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class NotificacaoEmail {
    private String host;
    private String port;
    private String email;
    private String senha;

    public NotificacaoEmail() {}

    public NotificacaoEmail(String host, String port, String email, String senha) {
        this.host = host;
        this.port = port;
        this.email = email;
        this.senha = senha;
    }

    public void enviarEmail(String enderecoEmail, String assunto, String corpoMenssagem) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        };

        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email));
        InternetAddress[] toAddresses = { new InternetAddress(enderecoEmail) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(assunto);
        msg.setSentDate(new java.util.Date());
        msg.setText(corpoMenssagem);

        Transport.send(msg);
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setPassword(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "NotificacaoEmail{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", email='" + email + '\'' +
                ", password='" + senha + '\'' +
                '}';
    }
}
