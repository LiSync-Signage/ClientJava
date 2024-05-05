package models;

import java.time.LocalDateTime;

public class LogComponente {
    private Integer fkComponente;
    private Double valor;
    private LocalDateTime dataHora;

    public LogComponente() {

    }
    public LogComponente(Integer fkComponente, Double valor) {
        this.fkComponente = fkComponente;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}


