package org.LiSync.models;

import java.util.ArrayList;
import java.util.List;

public class Componente {
    private Integer idComponente;
    private String modelo;
    private Integer fkTelevisao;
    private Integer fkTipoComponente;
    private List<Monitoramento> monitoramentos;

    public Componente() {}

    public Componente(String modelo, Integer fkTelevisao, Integer fkTipoComponente) {
        this.modelo = modelo;
        this.fkTelevisao = fkTelevisao;
        this.fkTipoComponente = fkTipoComponente;
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

    public Integer getFkTelevisao() {
        return fkTelevisao;
    }

    public void setFkTelevisao(Integer fkTelevisao) {
        this.fkTelevisao = fkTelevisao;
    }

    public Integer getFkTipoComponente() {
        return fkTipoComponente;
    }

    public void setFkTipoComponente(Integer fkTipoComponente) {
        this.fkTipoComponente = fkTipoComponente;
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
                ", fkTelevisao=" + fkTelevisao +
                ", fkTipoComponente=" + fkTipoComponente +
                ", monitoramentos=" + monitoramentos +
                '}';
    }
}
