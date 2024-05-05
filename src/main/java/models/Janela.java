package models;

import java.time.LocalDateTime;

public class Janela {
    private Integer idJanela;
    private Integer pidJanela;
    private String titulo;
    private String localizacao;
    private Integer visivel;
    private Integer fkTelevisao;

    public Janela(Integer pidJanela, String titulo, String localizacao, Integer visivel, Integer fkTelevisao) {
        this.pidJanela = pidJanela;
        this.titulo = titulo;
        this.localizacao = localizacao;
        this.visivel = visivel;
        this.fkTelevisao = fkTelevisao;
    }

    public Integer getIdJanela() {
        return idJanela;
    }

    public void setIdJanela(Integer idJanela) {
        this.idJanela = idJanela;
    }

    public Integer getPidJanela() {
        return pidJanela;
    }

    public void setPidJanela(Integer pidJanela) {
        this.pidJanela = pidJanela;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Integer getVisivel() {
        return visivel;
    }

    public void setVisivel(Integer visivel) {
        this.visivel = visivel;
    }

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }
}
