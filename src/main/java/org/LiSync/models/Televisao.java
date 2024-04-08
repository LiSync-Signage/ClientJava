package org.LiSync.models;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.*;
public class Televisao {
    private Integer idTelevisao;
    private String andar;
    private String setor;
    private Integer taxaAtualizacao;
    private String ipTv;
    private Boolean status;
    private String sistemaOperacional;
    private Integer fkEmpresa;
    private List<Componente> componentes;

    public Televisao() {}

    public Televisao(String andar, String setor, Integer taxaAtualizacao, String ipTv,
                     Boolean status, String sistemaOperacional, Integer fkEmpresa) {
        this.andar = andar;
        this.setor = setor;
        this.taxaAtualizacao = taxaAtualizacao;
        this.status = status;
        this.ipTv = ipTv;
        this.sistemaOperacional = sistemaOperacional;
        this.fkEmpresa = fkEmpresa;
        this.componentes = new ArrayList<>();
    }

    public void adicionarComponente(Componente componente) {
        this.componentes.add(componente);
    }

    public Integer getIdTelevisao() {
        return idTelevisao;
    }

    public void setIdTelevisao(Integer idTelevisao) {
        this.idTelevisao = idTelevisao;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Integer getTaxaAtualizacao() {
        return taxaAtualizacao;
    }

    public void setTaxaAtualizacao(Integer taxaAtualizacao) {
        this.taxaAtualizacao = taxaAtualizacao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public String getIpTv() {
        return ipTv;
    }

    public void setIpTv(String ipTv) {
        this.ipTv = ipTv;
    }

    @Override
    public String toString() {

        String statusTvAtual;
        if (getStatus().equals(true)) {
            statusTvAtual = "Tv conectada a Rede";
        } else {
            statusTvAtual = "Tv não está conectado a rede";
        }

        return "Televisao{" +
                "idTelevisao=" + idTelevisao +
                ", andar='" + andar + '\'' +
                ", setor='" + setor + '\'' +
                ", taxaAtualizacao=" + taxaAtualizacao +
                ", ipTv='" + ipTv + '\'' +
                ", status=" + status +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                ", fkEmpresa=" + fkEmpresa +
                ", componentes=" + componentes +
                '}';
    }
}

