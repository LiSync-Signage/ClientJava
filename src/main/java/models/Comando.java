package models;

public class Comando {
    private Integer idComando;
    private String comando;
    private Integer fkTelevisao;

    public Comando() {

    }
    public Comando(String comando, Integer fkTelevisao) {
        this.comando = comando;
        this.fkTelevisao = fkTelevisao;
    }


    public Integer getIdComando() {
        return idComando;
    }

    public void setIdComando(Integer idComando) {
        this.idComando = idComando;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }
}
