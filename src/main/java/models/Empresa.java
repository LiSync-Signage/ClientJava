package models;

import plano.Plano;

public class Empresa {
    private Integer idEmpresa;
    private String nomeFantasia;
    private Plano plano;
    private Integer qtdMaquinas = 0;

    public Empresa() {}

    public Empresa(String nomeFantasia, Plano plano) {
        this.nomeFantasia = nomeFantasia;
        this.plano = plano;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Integer getQtdMaquinasCadastradas() { return qtdMaquinas; }

    public void setQtdMaquinasCadastradas(Integer qtdMaquinas) {
        this.qtdMaquinas = qtdMaquinas;
    }

    @Override
    public String toString() {
        return """
               ID Empresa: %d
               Nome Fantasia: %s
               Plano: %s
               """.formatted(this.idEmpresa, this.nomeFantasia, this.plano);
    }
}
