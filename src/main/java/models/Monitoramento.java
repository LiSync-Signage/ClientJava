package models;

import java.time.LocalDateTime;

public class Monitoramento {
    protected LocalDateTime dataHora;
    protected Double valor;

    public Monitoramento() {}

    public Monitoramento(LocalDateTime dataHora, Double valor) {
        this.dataHora = dataHora;
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Monitoramento{" +
                "dataHora=" + dataHora +
                ", valor=" + valor +
                '}';
    }
}
