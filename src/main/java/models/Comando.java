package models;

public class Comando {
    private Integer idComando;
    private String nomeComando;
    private Integer fkTelevisao;

    public Comando() {

    }
    public Comando(Integer idComando,String comando, Integer fkTelevisao) {
        this.idComando = idComando;
        this.nomeComando = comando;
        this.fkTelevisao = fkTelevisao;
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
