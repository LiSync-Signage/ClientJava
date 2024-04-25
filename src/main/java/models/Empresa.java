package models;

public class Empresa {
    private Integer idEmpresa;
    private String nomeFantasia;
    private String plano;

    public Empresa() {}

    public Empresa(String nomeFantasia, String plano) {
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

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
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
