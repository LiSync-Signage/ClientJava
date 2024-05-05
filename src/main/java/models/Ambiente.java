package models;

public class Ambiente {
    Integer idAmbiente;
    String setor;
    String andar;
    Integer fkEmpresa;
    public Ambiente() {
    }
    public Ambiente(String setor, String andar, Integer fkEmpresa) {
        this.setor = setor;
        this.andar = andar;
        this.fkEmpresa = fkEmpresa;
    }



    public Integer getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
