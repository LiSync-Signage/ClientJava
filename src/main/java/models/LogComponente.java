package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogComponente {
    private Integer fkComponente;
    private Double valor;
    private LocalDateTime dataHora;

    private Componente componente;

    public LogComponente() {}


    public LogComponente(Integer fkComponente, Double valor) {
        this.fkComponente = fkComponente;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
        this.componente = componente;
    }

    public LogComponente(Integer fkComponente, Double valor, Componente componente) {
        this.fkComponente = fkComponente;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
        this.componente = componente;
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

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return String.format(
                "{ Data e Hora: %s }" +
                        " ID Componente: %d " +
                        "| Valor: %.2f " +
                        "| Modelo: '%s' " +
                        "| Tipo Componente: '%s' " +
                        "| Identificador: '%s' " +
                        "| ID Televisão: %d\n",
                dataHora.format(formatter),
                fkComponente,
                valor,
                componente.getModelo(),
                componente.getTipoComponente(),
                componente.getIdentificador(),
                componente.getFkTelevisao()
        );
    }


}

