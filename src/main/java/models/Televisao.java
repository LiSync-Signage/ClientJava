package models;

import java.util.ArrayList;
import java.util.List;

public class Televisao {
    private Integer idTelevisao;
    private String nomeTelevisao;
    private Integer taxaAtualizacao;
    private String hostname;
    private List<ComponenteTv> componentes;

    private Integer fkAmbiente;

    public Televisao(Integer idTelevisao, String nomeTelevisao, Integer taxaAtualizacao, String hostname, Integer fkAmbiente) {
        this.idTelevisao = idTelevisao;
        this.nomeTelevisao = nomeTelevisao;
        this.taxaAtualizacao = taxaAtualizacao;
        this.hostname = hostname;
        this.componentes = new ArrayList<>();
        this.fkAmbiente = fkAmbiente;
    }

    public Televisao(String nomeTelevisao, Integer fkAmbiente, Integer taxaAtualizacao, String hostname) {
        this.nomeTelevisao = nomeTelevisao;
        this.fkAmbiente = fkAmbiente;
        this.taxaAtualizacao = taxaAtualizacao;
        this.hostname = hostname;
        this.componentes = new ArrayList<>();
    }

    public Televisao() {

    }

    public Integer getFkAmbiente() {
        return fkAmbiente;
    }

    public void setFkAmbiente(Integer fkAmbiente) {
        this.fkAmbiente = fkAmbiente;
    }

    public void registarComponenteTv(ComponenteTv componente) {
        this.componentes.add(componente);
    }

    public Integer getIdTelevisao() {
        return idTelevisao;
    }

    public void setIdTelevisao(Integer idTelevisao) {
        this.idTelevisao = idTelevisao;
    }

    public String getNome() {
        return nomeTelevisao;
    }

    public void setNome(String nomeTelevisao) {
        this.nomeTelevisao = nomeTelevisao;
    }

    public Integer getTaxaAtualizacao() {
        return taxaAtualizacao;
    }

    public void setTaxaAtualizacao(Integer taxaAtualizacao) {
        this.taxaAtualizacao = taxaAtualizacao;
    }

    public String getHostName() {
        return hostname;
    }

    public void setHostName(String hostname) {
        this.hostname = hostname;
    }

    public List<ComponenteTv> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteTv> componentes) {
        this.componentes = componentes;
    }

    @Override
    public String toString() {
        return "Televisao{" +
                "idTelevisao=" + idTelevisao +
                ", nome='" + nomeTelevisao + '\'' +
                ", taxaAtualizacao=" + taxaAtualizacao +
                ", hostName='" + hostname + '\'' +
                ", componentes=" + componentes +
                ", fkAmbiente=" + fkAmbiente +
                '}';
    }
}

