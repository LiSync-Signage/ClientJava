package models;

import java.util.ArrayList;
import java.util.List;

public class Televisao {
    private Integer idTelevisao;
    private String nomeTelevisao;
    private Integer taxaAtualizacao;
    private String hostname;
    private List<Componente> componentes;

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

    public void registarComponente(Componente componente) {
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

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public String getNomeTelevisao() {
        return nomeTelevisao;
    }

    public void setNomeTelevisao(String nomeTelevisao) {
        this.nomeTelevisao = nomeTelevisao;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }



    @Override
    public String toString() {
        return "Televisao{" +
                "idTelevisao=" + idTelevisao +
                ", nomeTelevisao='" + nomeTelevisao + '\'' +
                ", taxaAtualizacao=" + taxaAtualizacao +
                ", hostname='" + hostname + '\'' +
                ", componentes=" + componentes +

                '}';
    }
}

