package models;

import java.util.ArrayList;
import java.util.List;

public class Televisao {
    private Integer idTelevisao;
    private String nome;
    private Integer taxaAtualizacao;
    private String hostName;
    private List<Componente> componentes;

    private Integer fkAmbiente;

    public Televisao(Integer idTelevisao, String nome, Integer taxaAtualizacao, String hostName, Integer fkAmbiente) {
        this.idTelevisao = idTelevisao;
        this.nome = nome;
        this.taxaAtualizacao = taxaAtualizacao;
        this.hostName = hostName;
        this.componentes = new ArrayList<>();
        this.fkAmbiente = fkAmbiente;
    }

    public Televisao(String nome, Integer fkAmbiente, Integer taxaAtualizacao, String hostName) {
        this.nome = nome;
        this.fkAmbiente = fkAmbiente;
        this.taxaAtualizacao = taxaAtualizacao;
        this.hostName = hostName;
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

    public void registarComponenteTv(Componente componente) {
        this.componentes.add(componente);
    }

    public Integer getIdTelevisao() {
        return idTelevisao;
    }

    public void setIdTelevisao(Integer idTelevisao) {
        this.idTelevisao = idTelevisao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTaxaAtualizacao() {
        return taxaAtualizacao;
    }

    public void setTaxaAtualizacao(Integer taxaAtualizacao) {
        this.taxaAtualizacao = taxaAtualizacao;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    @Override
    public String toString() {
        return "Televisao{" +
                "idTelevisao=" + idTelevisao +
                ", nome='" + nome + '\'' +
                ", taxaAtualizacao=" + taxaAtualizacao +
                ", hostName='" + hostName + '\'' +
                ", componentes=" + componentes +
                ", fkAmbiente=" + fkAmbiente +
                '}';
    }
}

