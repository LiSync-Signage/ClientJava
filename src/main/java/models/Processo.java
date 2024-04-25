package models;

import java.time.LocalDateTime;

public class Processo {
    private Integer idProcesso;
    private Integer pid;
    private String nome;
    private Integer fkTelevisao;
    private LocalDateTime dataHora;

    public Processo() {}

    public Processo(Integer pid, String nome, Integer fkComputador) {
        this.pid = pid;
        this.nome = nome;
        this.fkTelevisao = fkComputador;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "idProcesso=" + idProcesso +
                ", pid=" + pid +
                ", nome='" + nome + '\'' +
                ", fkTelevisao=" + fkTelevisao +
                ", dataHora=" + dataHora +
                '}';
    }
}
