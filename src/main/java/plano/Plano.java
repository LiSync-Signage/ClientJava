package plano;

public enum Plano {
    Basico(10, "Basico"),
    Corporativo(25, "Corporativo"),
    Enterprise(50, "Enterprise");

    private Integer qtdTvs;
    private String titulo;

    Plano(Integer qtdTvs, String titulo) {
        this.qtdTvs = qtdTvs;
        this.titulo = titulo;

    }

    public Integer getQtdTvs() {
        return qtdTvs;
    }
    public String getTitulo(){
        return titulo;
    }
}
