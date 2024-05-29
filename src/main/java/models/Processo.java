package models;

import java.time.LocalDateTime;

public class Processo {
    protected Integer idLog;
    protected Integer pid;
    protected LocalDateTime dataHora;
    protected String nomeProcesso;
    protected Double valor;
    protected Integer fkComponente;

    public Processo() {}

    public Processo(Integer pid, String nomeProcesso, Integer fkComponente, Double valor) {
        this.pid = pid;
        this.nomeProcesso = nomeProcesso;
        this.fkComponente = fkComponente;
        this.dataHora = LocalDateTime.now();
        this.valor = valor;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
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

    public void setIdLog(Integer idLog) {
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
                "idLog=" + idLog +
                ", pid=" + pid +
                ", nomeProcesso='" + nomeProcesso + '\'' +
                ", dataHora=" + dataHora +
                ", fkComponente=" + fkComponente +
                ", valor=" + valor +
                '}';
    }
}
