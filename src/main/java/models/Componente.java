package models;

import java.util.ArrayList;
import java.util.List;

public class Componente {
    private Integer idComponente;
    private String modelo;
    private String identificador;
    private String tipoComponente;
    private Integer fkTelevisao;
    private List<Monitoramento> monitoramentos;

    public Componente() {}

    public Componente(String modelo, String identificador, String tipoComponente, Integer fkTelevisao) {
        this.modelo = modelo;
        this.identificador = identificador;
        this.tipoComponente = tipoComponente;
        this.fkTelevisao = fkTelevisao;
        this.monitoramentos = new ArrayList<>();
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }

    public List<Monitoramento> getMonitoramentos() {
        return monitoramentos;
    }

    public void setMonitoramentos(List<Monitoramento> monitoramentos) {
        this.monitoramentos = monitoramentos;
    }

    @Override
    public String toString() {
        return "Componente{" +
                "idComponente=" + idComponente +
                ", modelo='" + modelo + '\'' +
                ", identificador='" + identificador + '\'' +
                ", tipoComponente='" + tipoComponente + '\'' +
                ", fkTelevisao=" + fkTelevisao +
                ", monitoramentos=" + monitoramentos +
                '}';
    }
}
