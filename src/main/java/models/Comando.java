package models;

public class Comando {
    private Integer idComando;
    private String nomeComando;
    private String resposta;
    private Integer fkTelevisao;

    public Comando() {

    }
    public Comando(Integer idComando,String comando, String resposta, Integer fkTelevisao) {
        this.idComando = idComando;
        this.nomeComando = comando;
        this.resposta = resposta;
        this.fkTelevisao = fkTelevisao;
    }


    public Comando(String comandoExecutado, String s, Integer fktelevisao) {
        this.nomeComando = comandoExecutado;
        this.resposta = s;
        this.fkTelevisao = fktelevisao;
    }


    public Integer getIdComando() {
        return idComando;
    }

    public void setIdComando(Integer idComando) {
        this.idComando = idComando;
    }

    public String getnomeComando() {
        return nomeComando;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setnomeComando(String comando) {
        this.nomeComando = comando;
    }

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }
}
