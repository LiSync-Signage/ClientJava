package models;

import java.time.LocalDateTime;

public class Processo {
    protected Integer idLog;
    protected Integer pid;
    protected LocalDateTime dataHora;
    protected String nomeProcesso;
    protected Double valor;
    protected Integer idComponente;

    public Processo() {}

    public Processo(Integer pid, String nomeProcesso, Integer idComponente, Double valor) {
        this.pid = pid;
        this.nomeProcesso = nomeProcesso;
        this.idComponente = idComponente;
        this.dataHora = LocalDateTime.now();
        this.valor = valor;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idProcesso) {
        this.idLog = idLog;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nome) {
        this.nomeProcesso = nomeProcesso;
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
                "idProcesso=" + idLog +
                ", pid=" + pid +
                ", nome='" + nomeProcesso + '\'' +
                ", dataHora=" + dataHora +
                ", idComponente=" + idComponente +
                ", valor=" + valor +
                '}';
    }
}
