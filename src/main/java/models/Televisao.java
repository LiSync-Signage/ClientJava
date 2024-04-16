package models;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.*;
public class Televisao {
    private Integer idTelevisao;
    private String andar;
    private String setor;
    private String nome;
    private Integer taxaAtualizacao;
    private String hostName;
    private Integer fkEmpresa;
    private List<Componente> componentes;

    public Televisao() {}

    public Televisao(String andar, String setor, String nome, Integer taxaAtualizacao,
                     String hostName, Integer fkEmpresa) {
        this.andar = andar;
        this.setor = setor;
        this.nome = nome;
        this.taxaAtualizacao = taxaAtualizacao;
        this.hostName = hostName;
        this.fkEmpresa = fkEmpresa;
        this.componentes = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Televisao{" +
                "idTelevisao=" + idTelevisao +
                ", andar='" + andar + '\'' +
                ", setor='" + setor + '\'' +
                ", nome='" + nome + '\'' +
                ", taxaAtualizacao=" + taxaAtualizacao +
                ", hostName='" + hostName + '\'' +
                ", fkEmpresa=" + fkEmpresa +
                ", componentes=" + componentes +
                '}';
    }
}

